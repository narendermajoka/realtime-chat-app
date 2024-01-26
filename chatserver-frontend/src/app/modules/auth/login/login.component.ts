import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/app/core/services/http.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(public router:Router,private httpService: HttpService) { }

  username:any;
  password:any;

  ngOnInit() {
    localStorage.clear();
  }
  
  login(){
    let authRequest = {};
    authRequest['username'] = this.username;
    authRequest['password'] = this.password;
    localStorage.clear();

    this.httpService.post("/auth/generate/token", authRequest)
    .subscribe((res)=>{
        if(res.data!=null){
          let jwtToken = res.data;
          let payload = JSON.parse(atob(jwtToken.split('.')[1]));
          localStorage.setItem("loggedInUserId", payload.userId);
          localStorage.setItem("userFullName", payload.userFullName);
          localStorage.setItem("id_token", jwtToken);
        }
        this.router.navigate(['/home']);
    });
    this.username = null;
    this.password = null;
  }

}
