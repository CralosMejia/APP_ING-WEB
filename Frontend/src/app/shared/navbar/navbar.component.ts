import { Component, OnInit,Inject } from '@angular/core';
import { FormBuilder, FormGroup} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { NavbarService } from 'src/app/services/communication/navbar.service';
import { UserService } from 'src/app/services/user.service';
import { DOCUMENT } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public searchForm: FormGroup;
  public parameter:string;
  public idUserLogin:string;

  profileJson: string = null;

  private searchparameter = new Subject<string>();
    searchparameter$ = this.searchparameter.asObservable();

  constructor(
              private userSrv: UserService,
              private router:Router,
              private fb:FormBuilder,
              private cSrv: NavbarService,
              private route:ActivatedRoute,
              @Inject(DOCUMENT) public document: Document,
              public auth: AuthService
  ) {
  }

  ngOnInit(): void {

    this.auth.user$.subscribe(
      (profile) => (this.profileJson = JSON.stringify(profile, null, 2))
    );
    console.log(this.profileJson);

    this.getUserID();

    this.searchForm= this.fb.group({
      search:['']
    });
  }

  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

  logout(){
    // this.userSrv.logout();
    this.auth.logout({
      logoutParams: {
        returnTo: 'https://frontend-core-angular-ingweb2022-2023.onrender.com/#/login'
      }
    });
    // this.router.navigateByUrl('/login');
  }

  navigatetoShoppingCar(){
    this.router.navigate(['/start/shoppingcar'],{queryParams: {idUser:this.idUserLogin}});
  }

  navigatetohome(){
    this.router.navigate(['/start/customer'],{queryParams: {idUser:this.idUserLogin}});
  }

  navigatetoYourPurchases(){
    this.router.navigate(['/start/purchases'],{queryParams: {idUser:this.idUserLogin}});
  }

  navigatetoStores(){
    this.router.navigate(['/start/stores'],{queryParams: {idUser:this.idUserLogin}});
  }

  search(){
    this.parameter = this.searchForm.get('search').value;
    if(this.parameter  === "" || this.parameter === undefined){
      this.parameter = "allproductstobuy";
    }

    this.cSrv.changeSearchParameter(this.parameter);

  }



}
