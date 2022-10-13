import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  public registerForm: FormGroup;

  constructor(
    private userSrv: UserService,
    private fb:FormBuilder,
    private router:Router
  ) { }

  ngOnInit(): void {

    this.registerForm= this.fb.group({
      name:['',[Validators.required]],
      email:['',[Validators.required, Validators.email]],
      password:['',[Validators.required]],
      passwordRepeat:['',[Validators.required]]
    });
  }

  createUser(){
    if(this.registerForm.invalid){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'all fields must be completed'
      })
      return;
    }

    if(!this.checkPasswords()){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Passwords do not match'
      })
      return;
    }
    this.userSrv.createUser(this.registerForm.value).subscribe(() => {
        this.router.navigateByUrl('/');
    });
  }

  checkPasswords(){
    let pass1=this.registerForm.get('password').value;
    let pass2=this.registerForm.get('passwordRepeat').value;
    if(pass1 === pass2){
      return true;
    }
    return false;
  }

}
