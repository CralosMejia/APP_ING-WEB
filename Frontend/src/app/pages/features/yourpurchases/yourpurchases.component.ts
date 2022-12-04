import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Sale } from 'src/app/models/sale';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-yourpurchases',
  templateUrl: './yourpurchases.component.html',
  styleUrls: ['./yourpurchases.component.css']
})
export class YourpurchasesComponent implements OnInit {

  public idUserLogin:string;
  public productsList: Sale[]=[];

  constructor(
    private saleSrv:SaleService,
    private route:ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.getUserID();
    this.loadSales();
  }

  loadSales(){
    this.saleSrv.getProducts(this.idUserLogin).subscribe((resp:any)=>{
      this.productsList = resp.Sales;
    })
  }
  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

}
