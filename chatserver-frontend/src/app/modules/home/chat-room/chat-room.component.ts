import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from 'src/app/core/services/http.service';

declare var SockJS;
declare var Stomp;
import {environment} from 'src/environments/environment';


@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.scss']
})
export class ChatRoomComponent implements OnInit, OnDestroy {

  constructor(private route: ActivatedRoute, private httpService: HttpService) {}
  

  messages=[];
  room:any = "";
  stompClient;
  loggedInUserId;

  ngOnInit() {
    this.loggedInUserId = localStorage.getItem('loggedInUserId');
    let roomStr = this.route.snapshot.paramMap.get('data');
    this.room = JSON.parse(roomStr); 
    let url  = "/api/v1/chat/room/"+ this.room.chatRoomId+"/messages"
    this.httpService.get(url)
    .subscribe((res)=>{
        this.messages = res.data;
    });
    this.connectToChatRoom(this.room.chatRoomId, (msg: any)=>{
      if(msg){
        this.messages.push(msg);
      }
    });
  }
  
  input;
  sendMessage() {
    if (this.input) {
      let ob = {};
      ob['senderId'] = this.loggedInUserId;
      ob['textMessage'] = this.input;
      ob['chatRoomId'] = this.room.chatRoomId;
      
      let destination = '/app/message/chat-room';
      let messageBody = JSON.stringify(ob);
      console.log(messageBody);
      this.stompClient.send( destination, {}, messageBody);
      this.input = '';
    }
  }
  
  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.sendMessage();
    }
  }

  connectToChatRoom(chatRoomId, callback){
    const serverUrl = environment.app_url_ws;
    console.log(serverUrl);
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    const room = '/topic/'+ chatRoomId;

    this.stompClient.connect({}, function(frame) {
      
      that.stompClient.subscribe(room, (message) => {
        if (message.body) {
          console.log("Got message from chat room:");
          let chatMessage = JSON.parse(message.body);
          console.log(chatMessage);
          callback(chatMessage);
        }
      });
    });    
  }

  ngOnDestroy(): void {
    this.disconnectFromRoom();
  }

  disconnectFromRoom(){
    this.stompClient.disconnect();
  }
  

}
