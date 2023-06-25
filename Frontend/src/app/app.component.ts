import { Component,Inject,OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Frontend';
  constructor(
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService,
    private router:Router,
    private userSrv: UserService,
) {
}
  ngOnInit(): void {


    this.auth.isAuthenticated$.subscribe((par)=>{

      if(par){
        this.auth.user$.subscribe((par)=>{
          this.userSrv.login(par.email,par.nickname).subscribe((resp)=> {
                 this.router.navigate(['/start/customer'],{queryParams: {idUser: resp.User.id}});})

      })
    }})


  }

}
