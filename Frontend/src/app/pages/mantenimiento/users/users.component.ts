import { identifierName } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
  private idUserLogin;
  public isAdmin:boolean = false;

  constructor(private userSrv: UserService,
              private fb:FormBuilder,
              private route:ActivatedRoute
    ) { }

  ngOnInit(): void {
    
    

    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })

    this.userSrv.getUserRol(this.idUserLogin).subscribe((res:any)=>{
      if(res.Rol === "ADMIN"){
        this.isAdmin = true;
      }
    })

    this.userForm= this.fb.group({
      name:['', Validators.required],
      email:['',[Validators.required, Validators.email]]
    });
   
    this.loadUsers();
  }

  loadUsers(){
    this.userSrv.getUsers().subscribe((users:any)=> {
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
        this.userSrv.deleteUser(id,this.idUserLogin).subscribe((res)=>{this.loadUsers()},error=>{
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
    this.userSrv.updateUser(user,this.idUserLogin).subscribe(() => {
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


}
