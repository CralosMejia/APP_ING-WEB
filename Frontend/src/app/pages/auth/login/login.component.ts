import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  public userForm: FormGroup;

  constructor(
    private userSrv: UserService,
    private fb:FormBuilder
  ) { 
    this.userForm= this.fb.group({
      email:['',[Validators.required, Validators.email]],
      password:['',[Validators.required]]
    });
  }

  ngOnInit(): void {
  }

  login(){}

}
