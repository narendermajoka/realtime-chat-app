import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpService } from 'src/app/core/services/http.service';

declare var SockJS;
declare var Stomp;
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.scss']
})
export class ChatRoomComponent implements OnInit, OnDestroy {

  constructor(private route: ActivatedRoute, private router: Router, private httpService: HttpService) { }


  messages = [];
  room: any = "";
  stompClient;
  loggedInUserId;
  userFullName;
  sendMessageUrl;
  ngOnInit() {
    this.loggedInUserId = localStorage.getItem('loggedInUserId');
    this.userFullName = localStorage.getItem('userFullName');

    let roomStr = this.route.snapshot.paramMap.get('data');
    this.room = JSON.parse(roomStr);

    this.sendMessageUrl = '/app/message/chat-room/' + this.room.chatRoomId;

    let url = "/api/v1/chat/room/" + this.room.chatRoomId + "/messages";
    let that = this;
    this.httpService.get(url)
      .subscribe(
        (res) => {
          this.messages = res.data;
        },
        error => {
          that.router.navigate(['home/dashboard']);
        });

    this.connectToChatRoom(this.room.chatRoomId, (msg: any) => {
      if (msg) {
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
      ob['messageType'] = "MESSAGE";
      ob['chatRoomId'] = this.room.chatRoomId;

      let messageBody = JSON.stringify(ob);
      this.stompClient.send(this.sendMessageUrl, {}, messageBody);
      this.input = '';
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.sendMessage();
    }
  }

  connectToChatRoom(chatRoomId, callback) {
    const serverUrl = environment.app_url_ws;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    const room = '/topic/chatrooms.' + chatRoomId;

    this.stompClient.connect({}, function (frame) {

      that.stompClient.subscribe(room, (message) => {
        if (message.body) {
          let chatMessage = JSON.parse(message.body);
          callback(chatMessage);
        }
      });

      let ob = {};
      ob['senderId'] = that.loggedInUserId;
      ob['messageType'] = "JOIN";
      ob['chatRoomId'] = that.room.chatRoomId;
      ob['senderFullName'] = that.userFullName;

      let messageBody = JSON.stringify(ob);
      that.stompClient.send(that.sendMessageUrl, {}, messageBody);

    });
  }

  ngOnDestroy(): void {
    this.disconnectFromRoom();
    this.stompClient.disconnect();
  }

  disconnectFromRoom() {
    let ob = {};
    let that = this;
    ob['senderId'] = that.loggedInUserId;
    ob['messageType'] = "LEAVE";
    ob['chatRoomId'] = that.room.chatRoomId;
    ob['senderFullName'] = that.userFullName;

    let messageBody = JSON.stringify(ob);
    that.stompClient.send(that.sendMessageUrl, {}, messageBody);
  }


}
