import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {

  constructor(
              private userSrv: UserService,
              private router:Router,
  ) { }

  ngOnInit(): void {
  }

  logout(){
    this.userSrv.logout();
    this.router.navigateByUrl('/login');
  }

}
