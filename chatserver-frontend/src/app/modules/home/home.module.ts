import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeRoutingModule } from './home-routing';
import { FormsModule } from '@angular/forms';
import { ChatRoomComponent } from './chat-room/chat-room.component';

@NgModule({
  declarations: [DashboardComponent, ChatRoomComponent],
  imports: [
    HomeRoutingModule,
    CommonModule,
    FormsModule
  ]
})
export class HomeModule { }
