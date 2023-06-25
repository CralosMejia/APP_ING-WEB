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
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { SharedModule } from '../shared/shared.module';
import { CustomerComponent } from './features/customer/customer.component';
import { ComponentsModule } from '../components/components.module';
import { ShoppingcarComponent } from './features/shoppingcar/shoppingcar.component';
import { YourpurchasesComponent } from './features/yourpurchases/yourpurchases.component';
import { SellerComponent } from './features/seller/seller.component';
import { StoreComponent } from './features/seller/store/store.component';



@NgModule({
  declarations: [
    UsersComponent,
    PagesComponent,
    LoginComponent,
    RegisterComponent,
    ProductsComponent,
    StoresComponent,
    CustomerComponent,
    ShoppingcarComponent,
    YourpurchasesComponent,
    SellerComponent,
    StoreComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    SharedModule,
    ComponentsModule,




  ],
  exports:[
    UsersComponent
  ]
})
export class PagesModule { }
