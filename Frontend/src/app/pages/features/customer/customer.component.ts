import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavbarService } from 'src/app/services/communication/navbar.service';
import { ProductCardsService } from 'src/app/services/communication/product-cards.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  public search:string;
  public idUserLogin:string;

  constructor(
      private cSrv:NavbarService,
      private prodCarSrv:ProductCardsService,
      private route:ActivatedRoute
  ) { }

  ngOnInit(): void {

    this.getUserID();
    
    this.cSrv.getSearchParameter$().subscribe(parameter =>{
      this.search = parameter;

      this.prodCarSrv.changeSearchParameter(this.search,this.idUserLogin);
    })

  }

  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

}
