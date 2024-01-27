import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/app/core/services/http.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  availableChatRooms = [];
  loggedInUserId;
  
  constructor(private router: Router, private httpService: HttpService) { }

  ngOnInit(): void {
    this.loggedInUserId = localStorage.getItem('loggedInUserId');
    this.fetchAvailableChatRooms();


  }
  fetchAvailableChatRooms() {
    this.httpService.get("/api/v1/chat/room")
      .subscribe((res) => {
        this.availableChatRooms = res.data;
      });

  }

  joinChatRoom(room) {
    let url = "/api/v1/chat/room/"+room.chatRoomId+"/join/user/"+ this.loggedInUserId;
    this.httpService.put(url)
      .subscribe((res) => {
        console.log(res.message);
        this.openChatRoom(room);
      });

  }

  openChatRoom(room) {
    this.router.navigate(['home/chat-room', { data: JSON.stringify(room) }]);
  }


}
