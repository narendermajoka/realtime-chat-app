import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/app/core/services/http.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{
  
  availableChatRooms=[];

  constructor(private router:Router, private httpService: HttpService){}

  ngOnInit(): void {
    this.fetchAvailableChatRooms();
    
    
  }
  fetchAvailableChatRooms() {
    this.httpService.get("/api/v1/chat/room")
    .subscribe((res)=>{
        this.availableChatRooms = res.data;
    });

  }

  joinChatRoom(room){
      this.router.navigate(['home/chat-room',{data: JSON.stringify(room)}]);
  }


}
