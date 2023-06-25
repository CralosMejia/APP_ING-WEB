import { Component, EventEmitter, OnInit, Output,Inject  } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';
import { AuthService } from '@auth0/auth0-angular';
import { DOCUMENT } from '@angular/common'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;

  constructor(
    private userSrv: UserService,
    private fb:FormBuilder,
    private router:Router,
    public auth: AuthService,
    @Inject(DOCUMENT) public document: Document
  ) {
    this.loginForm= this.fb.group({
      email:['',[Validators.required, Validators.email]],
      password:['',[Validators.required]]
    });
     this.auth.loginWithRedirect();
  }

  ngOnInit(): void {

  }

  login(){
    // if(!this.loginForm.invalid){
    //   this.userSrv.login(this.loginForm.value).subscribe((resp)=> {
    //     this.router.navigate(['/start/customer'],{queryParams: {idUser: resp.User.id}});
    //   },error =>{
    //     if(error.status === 409){
    //       Swal.fire({
    //         icon: 'error',
    //         title: 'Oops...',
    //         text: 'Something went wrong!'
    //       })
    //     }
    //   })
    // }

  }

}
