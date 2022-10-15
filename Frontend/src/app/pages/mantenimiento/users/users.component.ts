import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { UserService } from 'src/app/services/user.service';

import Swal from 'sweetalert2'

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  usersList: User[]=[];
  public userForm: FormGroup;
  private auxId:string;

  constructor(private userSrv: UserService,
              private fb:FormBuilder,
              private router:Router
    ) { }

  ngOnInit(): void {

    this.userForm= this.fb.group({
      name:['', Validators.required],
      email:['',[Validators.required, Validators.email]]
    });
   
    this.loadUsers();
  }

  loadUsers(){
    this.userSrv.getUsers().subscribe((users:any)=> {
      console.log(users);
      this.usersList =[];
      this.usersList = users.Users;
    });
  }

  deleteUser(id:string){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
        this.userSrv.deleteUser(id).subscribe((res)=>{this.loadUsers()},error=>{
          if(error.status === 500){
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: "You can't delete yourself"
            })
            console.clear();
          }
      });
      }
    })
  }

  updateFormUser(user: User){
    this.auxId = user.id;
    this.userForm.setValue({
      name:user.name,
      email:user.email
    });   
  }

  updateUser(){
    if(this.userForm.invalid){
      return;
    }
      
    let user:User = this.userForm.value;
    user.id = this.auxId;
    this.userSrv.updateUser(user).subscribe(() => {
      this.loadUsers(); 
      Swal.fire('User Udated');

    });
    this.cleanForm();
    
  }

  cleanForm(){
    this.userForm.setValue({
      name:"",
      email:""
    }); 
  }

  logout(){
    this.userSrv.logout();
    this.router.navigateByUrl('/login');
  }

}
