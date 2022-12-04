import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-shoppingcar',
  templateUrl: './shoppingcar.component.html',
  styleUrls: ['./shoppingcar.component.css']
})
export class ShoppingcarComponent implements OnInit {
  
  public idUserLogin:string;
  public productsList: Product[]=[];
  public total:number = 0;
  public amountForm: FormGroup;


  constructor(
    private userSrv: UserService,
    private route:ActivatedRoute,
    private fb:FormBuilder,
  ) { }

  ngOnInit(): void {

    this.amountForm= this.fb.group({
      value:[1, Validators.required]
    });

    this.getUserID();
    this.loadShoppingCar();
  }

  async loadShoppingCar(){
    await this.userSrv.getShoppingCar(this.idUserLogin).subscribe((products:any) =>{
      this.productsList =[];
      this.total=0;
      this.productsList = products.ShoppingCar;
      this.productsList.forEach(prod => {
          this.total = this.total + (prod.amount * prod.price);
      });
    });
  }
  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

  deleteProductShoppingCar(productId:string){

    let amount = Number((<HTMLInputElement>document.getElementById(`input-number-${productId}`)).value);
    if(amount <= 0){
      return;
    }

    this.userSrv.deleteProductToShoppingCar(this.idUserLogin,productId,amount).subscribe(()=>this.loadShoppingCar());
  }

  buy(){
    this.userSrv.buy(this.idUserLogin).subscribe(()=>this.loadShoppingCar());
  }

}
