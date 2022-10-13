import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router'
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register/register.component';
import { UsersComponent } from './mantenimiento/users/users.component';




const routes:Routes=[
    {path:'', component:UsersComponent},
    {path:'login', component:LoginComponent},
    {path:'register', component:RegisterComponent},


  
];
 
@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports:[RouterModule]
})
export class PagesRoutingModule { }