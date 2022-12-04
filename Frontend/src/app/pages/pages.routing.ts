import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router'
import { AuthGuard } from '../guards/auth-guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register/register.component';
import { CustomerComponent } from './features/customer/customer.component';
import { SellerComponent } from './features/seller/seller.component';
import { StoreComponent } from './features/seller/store/store.component';
import { ShoppingcarComponent } from './features/shoppingcar/shoppingcar.component';
import { YourpurchasesComponent } from './features/yourpurchases/yourpurchases.component';
import { StoresComponent } from './mantenimiento/stores/stores.component';
import { UsersComponent } from './mantenimiento/users/users.component';
import { PagesComponent } from './pages.component';




const routes:Routes=[
    {path:'start', component:PagesComponent, canActivate:[AuthGuard],children:
      [
        {path:'customer', component:CustomerComponent},
        {path:'shoppingcar', component:ShoppingcarComponent},
        {path:'purchases', component:YourpurchasesComponent},
        {path:'stores', component:SellerComponent},
        {path:'Storedetails', component:StoreComponent}

      ]
    },
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