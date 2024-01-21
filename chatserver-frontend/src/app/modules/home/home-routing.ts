import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ChatRoomComponent } from './chat-room/chat-room.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/home/dashboard',
    pathMatch: 'full'
  },
    {
      path: '',
      children: [
        {
          path: 'dashboard',
          component: DashboardComponent
        },
        {
          path: 'chat-room',
          component: ChatRoomComponent
        }
      ]
    },
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule { }
