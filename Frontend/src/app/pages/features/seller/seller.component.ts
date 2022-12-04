import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from 'src/app/models/Store';
import { StoreService } from 'src/app/services/store.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-seller',
  templateUrl: './seller.component.html',
  styleUrls: ['./seller.component.css']
})
export class SellerComponent implements OnInit {
  
  public idUserLogin:string;
  public isCreated:boolean= false;
  public storeFormCreate: FormGroup;
  public storesList:Store[];


  constructor(
    private fb:FormBuilder,
    private route:ActivatedRoute,
    private storeSrv:StoreService,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.getUserID();
    this.loadStores();

    this.storeFormCreate= this.fb.group({
      name:['', Validators.required],
      address:['',[Validators.required]]
    });
  }

  loadStores(){
    this.storeSrv.getStoresForUser(this.idUserLogin).subscribe((resp)=>{
      this.storesList=[];
      this.storesList = resp;
    })
  }

  getUserID(){
    this.route.queryParams.subscribe(params=>{
      this.idUserLogin = params['idUser'] || "0"
    })
  }

  clickButtonCreate(){
    if(this.isCreated){
      this.isCreated=false;
    }else{
      this.isCreated=true;
    }
  }

  createStore(){
    if(this.storeFormCreate.invalid){
      return;
    }
    
    let name= this.storeFormCreate.controls['name'].value;
    let address= this.storeFormCreate.controls['address'].value;
    let owner = this.idUserLogin;
    let store:Store = {name,address,owner};

    this.storeSrv.createStore(store, this.idUserLogin).subscribe(()=>this.loadStores());

    this.cleanForm();
  }
  
  seeStore(storeId:string){
    this.router.navigate(['/start/Storedetails'],{queryParams: {idUser:this.idUserLogin,
    idProducto:storeId}});
  }

  cleanForm(){
    this.storeFormCreate.setValue({
      name:"",
      address:""
    });  
  }

}
