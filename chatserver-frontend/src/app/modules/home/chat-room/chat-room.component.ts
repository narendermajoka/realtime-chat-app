import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

declare var SockJS;
declare var Stomp;
import {environment} from 'src/environments/environment';


@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.scss']
})
export class ChatRoomComponent implements OnInit, OnDestroy {

  constructor(private route: ActivatedRoute) {}
  

  messages=[];
  room:any = "";
  stompClient;

  ngOnInit() {
    //fetch all messages for this room from db
    let roomStr = this.route.snapshot.paramMap.get('data');
    this.room = JSON.parse(roomStr); 
    console.log("joined chat room: ");
    console.log(roomStr);
    this.connectToChatRoom(this.room.roomName, (msg: any)=>{
      if(msg){
        this.messages.push(msg);
      }
    });
  }
  
  input;
  sendMessage() {
    if (this.input) {
      let ob = {};
      ob['senderName'] = "Narender";
      ob['message'] = this.input;
      ob['receiverName'] = this.room.roomName;
      
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

  connectToChatRoom(roomName, callback){
    const serverUrl = environment.app_url;
    console.log("---------------------------------");
    console.log(serverUrl);
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    const room = '/topic/'+ roomName;

    this.stompClient.connect({}, function(frame) {
      
      that.stompClient.subscribe(room, (message) => {
        if (message.body) {
          console.log("Got message::::: ");
          let chatMessage = JSON.parse(message.body);
          console.log(chatMessage);
          callback(chatMessage.message);
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
