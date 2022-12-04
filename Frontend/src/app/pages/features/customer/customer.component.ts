import { Component, OnInit } from '@angular/core';
import { NavbarService } from 'src/app/services/communication/navbar.service';
import { ProductCardsService } from 'src/app/services/communication/product-cards.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  public search:string;

  constructor(
      private cSrv:NavbarService,
      private prodCarSrv:ProductCardsService
  ) { }

  ngOnInit(): void {

      
    this.cSrv.getSearchParameter$().subscribe(parameter =>{
      this.search = parameter;

      this.prodCarSrv.changeSearchParameter(this.search);
    })

  }

}
