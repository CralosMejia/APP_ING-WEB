import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { UsersComponent } from './mantenimiento/users/users.component';
import { PagesComponent } from './pages.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    UsersComponent,
    PagesComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    
  ],
  exports:[
    UsersComponent
  ]
})
export class PagesModule { }
