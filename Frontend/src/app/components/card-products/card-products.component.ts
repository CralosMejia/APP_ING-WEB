import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { ProductCardsService } from 'src/app/services/communication/product-cards.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-card-products',
  templateUrl: './card-products.component.html',
  styleUrls: ['./card-products.component.css']
})
export class CardProductsComponent implements OnInit {

  public arrayProducts:Product[] = [];
  public amountForm: FormGroup;
  public idUserLogin:string;
  
  constructor(
    private prodCardSrv: ProductCardsService,
    private proSrv:ProductService,
    private fb:FormBuilder,
    private route:ActivatedRoute,
    private userSrv:UserService
  ) { }

  ngOnInit(): void {

    this.loadProducts();
    this.getUserID();
    
    this.prodCardSrv.getProducts$().subscribe(products=>{
      this.arrayProducts = products;
    })

    this.amountForm= this.fb.group({
      value:['', Validators.required]
    });
  }

  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

  loadProducts(){

    if(this.arrayProducts.length === 0){
      this.proSrv.getProducts().subscribe((products:any)=> {
        this.arrayProducts =[];
        this.arrayProducts = products.Products;
      });
    }
  }


  addProductShoppingCar(productId:string){

    if(this.amountForm.invalid){
      return;
    }

    let amount = Number((<HTMLInputElement>document.getElementById(`input-number-${productId}`)).value);

    

    this.userSrv.addProductToShoppingCar(this.idUserLogin,productId,amount).subscribe();
  }

}
