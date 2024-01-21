import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{
  
  availableChatRooms=[];

  constructor(private router:Router){}

  ngOnInit(): void {
    let ob = {};
    ob['roomId'] = 1;
    ob['roomName'] = "Java";
    this.availableChatRooms.push(ob);

    ob = {};
    ob['roomId'] = 2;
    ob['roomName'] = "Spring Boot";
    this.availableChatRooms.push(ob);

    ob = {};
    ob['roomId'] = 3;
    ob['roomName'] = "Angular";
    this.availableChatRooms.push(ob);
    
  }

  roomName:any;
  createRoom() {
    //call API to create new room
  }

  joinChatRoom(room){
      console.log(room);
      this.router.navigate(['home/chat-room',{data: JSON.stringify(room)}]);
  }


}
