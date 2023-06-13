import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Categoria, Product } from 'src/app/models/Product';
import { Sale } from 'src/app/models/sale';
import { Store } from 'src/app/models/Store';
import { ProductService } from 'src/app/services/product.service';
import { SaleService } from 'src/app/services/sale.service';
import { StoreService } from 'src/app/services/store.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {

  public idStore:string;
  public isEditedStore:boolean= false;
  public isCreatedProduct:boolean= false;
  public store:Store = {id:'0',name:'Name',address:'Address',owner:'1'};
  public idUserLogin:string;
  public arrayProducts:Product[] = [];
  public productsList: Sale[]=[];
  public searchParams:string[]=[];
  public createProdForm:FormGroup;



  constructor(
    private storeSrv:StoreService,
    private route:ActivatedRoute,
    private proSrv:ProductService,
    private fb:FormBuilder,
    private saleSrv:SaleService,

  ) {}


  ngOnInit(): void {
    this.getUserID();
    this.getstoreID();
    this.loadStore();
    this.loadProducts();

    this.createProdForm = this.fb.group({
      name:['', Validators.required],
      price:[0, Validators.required],
      amount:[0, Validators.required],
      categoria:['', Validators.required],
      description:['', Validators.required]

    })
  }

  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

  getstoreID(){
    this.route.queryParams.subscribe(params=>{
      this.idStore = params['idProducto'] || "0"
    })
  }

  loadStore(){

    this.storeSrv.findStore(this.idStore).subscribe((resp:any)=>{
      this.store = resp.Store;
    })

  }

  loadProducts(){

    if(this.arrayProducts.length === 0){
      this.proSrv.getProductsStore(this.idStore).subscribe((products:any)=> {
        this.arrayProducts =[];
        this.arrayProducts = products.Products;
      });
    }
  }


  editDetails(){
    if(this.isEditedStore){
      this.isEditedStore = false;
    }else{
      this.isEditedStore = true;
    }
  }

  createProductBool(){
    if(this.isCreatedProduct){
      this.isCreatedProduct = false;
    }else{
      this.isCreatedProduct = true;
    }
  }

  updateStore(){
    let name =(<HTMLInputElement>document.getElementById(`form-store-name`)).value;
    let address =(<HTMLInputElement>document.getElementById(`form-store-address`)).value;
    let owner = this.idUserLogin;
    let id = this.idStore;

    let store:Store = {id,name,address,owner};

    this.storeSrv.updateStore(store,this.idUserLogin).subscribe(()=>{
      this.loadStore();
      this.loadProducts();
      this.editDetails();
    });
  }

  updateProd(idProd:string){
    let name =(<HTMLInputElement>document.getElementById(`form-prod-name-${idProd}`)).value;
    let price =Number((<HTMLInputElement>document.getElementById(`form-prod-price-${idProd}`)).value);
    let amount =Number((<HTMLInputElement>document.getElementById(`form-prod-amount-${idProd}`)).value);
    let description =(<HTMLInputElement>document.getElementById(`form-prod-description-${idProd}`)).value;
    let storeID= this.idStore;
    let id = idProd;
    let categoria= (<HTMLInputElement>document.getElementById(`form-prod-categoria-${idProd}`)).value;


    let prod:Product ={id,name,price,amount,description,storeID,categoria};

    this.proSrv.updateProduct(prod,this.idUserLogin).subscribe(()=>{
      this.loadStore();
      this.loadProducts();
    })

  }

  createProd(){
    if(this.createProdForm.invalid){
      return;
    }

    let product:Product = this.createProdForm.value;
    product.storeID = this.idStore;
    this.proSrv.createProduct(product,this.idUserLogin).subscribe(() => {
      this.loadProducts();
      this.createProductBool();
      Swal.fire('Product Created');

    });
    this.cleanForm();


  }

  seeSales(idProd:string){
    this.saleSrv.getSalesProducts(idProd,this.idUserLogin).subscribe((resp:any)=>{
      this.productsList = resp.Sales;
    })
    this.saleSrv.getRelations(idProd).subscribe((resp:any)=>{
      this.searchParams = resp.ParamSearch;
    })
  }

  cleanForm(){
    this.createProdForm.setValue({
      name:"",
      description:"",
      price:0,
      amount:0
    });
  }



}
