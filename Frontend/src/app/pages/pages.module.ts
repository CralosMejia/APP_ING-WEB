import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { UsersComponent } from './mantenimiento/users/users.component';
import { PagesComponent } from './pages.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from './auth/register/register/register.component';
import { ProductsComponent } from './mantenimiento/products/products.component';
import { StoresComponent } from './mantenimiento/stores/stores.component';



@NgModule({
  declarations: [
    UsersComponent,
    PagesComponent,
    LoginComponent,
    RegisterComponent,
    ProductsComponent,
    StoresComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
    
  ],
  exports:[
    UsersComponent
  ]
})
export class PagesModule { }
