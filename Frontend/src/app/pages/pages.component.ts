import { AfterViewInit, Component, OnInit, ViewChild,Inject } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { DOCUMENT } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {

  profileJson: string = null;



  constructor(@Inject(DOCUMENT) public document: Document,
  public auth: AuthService ) { }

  ngOnInit(): void {

  }



}
