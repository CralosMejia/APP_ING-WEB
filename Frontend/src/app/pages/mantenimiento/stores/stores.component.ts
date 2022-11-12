import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Store } from 'src/app/models/Store';
import { User } from 'src/app/models/User.model';
import { StoreService } from 'src/app/services/store.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-stores',
  templateUrl: './stores.component.html',
  styleUrls: ['./stores.component.css']
})
export class StoresComponent implements OnInit {
  
  storeList: Store[]=[];
  public storeFormCreate: FormGroup;
  public storeFormUpdate: FormGroup;
  private auxId:string;
  private idUserLogin;
  public isAdmin:boolean = false;

  constructor(
              private userSrv: UserService,
              private fb:FormBuilder,
              private route:ActivatedRoute,
              private storeSrv: StoreService
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

    this.storeFormUpdate= this.fb.group({
      name:['', Validators.required],
      address:['',[Validators.required]]
    });

    this.storeFormCreate= this.fb.group({
      name:['', Validators.required],
      address:['',[Validators.required]]
    });
   
    this.loadStore();
  }

  loadStore(){
    this.storeSrv.getStores(this.idUserLogin).subscribe((stores:any)=> {
      this.storeList =[];
      this.storeList = stores.Stores;
    });
  }

  createStore(){
    if(this.storeFormCreate.invalid){
      return;
    }
    

    let store:Store = this.storeFormCreate.value;
    let user:User;
    store.id = this.auxId;

    this.userSrv.findUser(this.idUserLogin).subscribe((resp:any)=>{
      store.owner= resp.user;

      this.storeSrv.createStore(store,this.idUserLogin).subscribe(() => {
        this.loadStore(); 
        Swal.fire('Store updated');
  
      });
      
    })
    
    this.cleanForm();
  }

  updateStore(){
    if(this.storeFormUpdate.invalid){
      return;
    }

    let store:Store = this.storeFormUpdate.value;
    store.id = this.auxId;

    this.userSrv.findUser(this.idUserLogin).subscribe((resp:any)=>{
      store.owner= resp.user;

      this.storeSrv.updateStore(store,this.idUserLogin).subscribe(() => {
        this.loadStore(); 
        Swal.fire('Store Created');
  
      });
      
    })
    this.cleanForm();
  }

  deleteStore(id:string){
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
        this.storeSrv.deleteStore(id,this.idUserLogin).subscribe((res)=>{
          this.loadStore()
        });
      }
    })
  }

  updateFormStore(store: Store){
    this.auxId = store.id;
    this.storeFormUpdate.setValue({
      name:store.name,
      address:store.address
    });   
  }

  cleanForm(){
    this.storeFormCreate.setValue({
      name:"",
      address:""
    }); 

    this.storeFormUpdate.setValue({
      name:"",
      address:""
    }); 
  }

}
