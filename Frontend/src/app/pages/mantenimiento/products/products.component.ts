import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route } from '@angular/router';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  
  productsList: Product[]=[];
  public productFormCreate: FormGroup;
  public productFormUpdate: FormGroup;
  private auxId:string;
  private idUserLogin;
  public isAdmin:boolean = false;

  constructor(private userSrv: UserService,
              private fb:FormBuilder,
              private route:ActivatedRoute,
              private prodSrv: ProductService
    ) { }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })

    this.userSrv.getUserRol(this.idUserLogin).subscribe((res:any)=>{
      if(res.Rol === "ADMIN"){
        this.isAdmin = true;
      }
    })

    this.productFormUpdate= this.fb.group({
      name:['', Validators.required],
      description:['',[Validators.required]],
      price:[0,Validators.required]
    });

    this.productFormCreate= this.fb.group({
      name:['', Validators.required],
      description:['',[Validators.required]],
      price:[0,Validators.required]
    });
   
    this.loadProducts();
  }

  loadProducts(){
    this.prodSrv.getProducts(this.idUserLogin).subscribe((products:any)=> {
      this.productsList =[];
      this.productsList = products.Products;
    });
  }

  
  updateProduct(){
    if(this.productFormUpdate.invalid){
      return;
    }

    let product:Product = this.productFormUpdate.value;
    product.id = this.auxId;
    this.prodSrv.updateProduct(product,this.idUserLogin).subscribe(() => {
      this.loadProducts(); 
      Swal.fire('Product Updated');

    });
    this.cleanForm();
  }

  createProduct(){
    if(this.productFormCreate.invalid){
      return;
    }

    let product:Product = this.productFormCreate.value;
    product.id = this.auxId;
    this.prodSrv.createProduct(product,this.idUserLogin).subscribe(() => {
      this.loadProducts(); 
      Swal.fire('Product Created');

    });
    this.cleanForm();
  }

  deleteProd(id:string){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
        this.prodSrv.deleteProd(id,this.idUserLogin).subscribe((res)=>{
          this.loadProducts()
        });
      }
    })
  }
  
  updateFormProd(prod: Product){
    this.auxId = prod.id;
    this.productFormUpdate.setValue({
      name:prod.name,
      description:prod.description,
      price:prod.price
    });   
  }

  cleanForm(){
    this.productFormCreate.setValue({
      name:"",
      description:"",
      price:0
    }); 

    this.productFormUpdate.setValue({
      name:"",
      description:"",
      price:0
    }); 
  }

}
