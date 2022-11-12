import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router'
import { AuthGuard } from '../guards/auth-guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register/register.component';
import { UsersComponent } from './mantenimiento/users/users.component';
import { PagesComponent } from './pages.component';




const routes:Routes=[
    {path:'', component:PagesComponent, canActivate:[AuthGuard]},
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