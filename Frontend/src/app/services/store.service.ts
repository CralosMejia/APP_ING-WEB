import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Product } from '../models/Product';
import { Store } from '../models/Store';
import { User } from '../models/User.model';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private http: HttpClient) { }

  get token():string{
   
    return localStorage.getItem('token') || '';

  }

  get headers(){
    
    return new HttpHeaders().set('Content-Type', 'application/json').append('token',this.token);
    
  }


  getStores(userLoginID: string){
    const url = `${base_url}/Stores/entry`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      })
    })
  }



  createStore(store: any,userLoginID: string){
    return this.http.post(`${base_url}/Stores/create`,store);
  }

  deleteStore(id:String, userLoginID: string){
    const url = `${base_url}/Stores/delete/${id}`;
    return this.http.delete(url,{
      headers: this.headers,
      body:userLoginID
   });
  }

  updateStore(store:Store,userLoginID: string){
    const url = `${base_url}/Stores/update/${store.id}`;
    return this.http.put(url,{
        "store":store,
        "id":userLoginID
    },{
      headers: {
        'token':this.token
      }
    });
  }

  getStoresForUser(userLoginID: string){
    const url = `${base_url}/Stores/entrySe/${userLoginID}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      })
    }).pipe(
      map((resp:any)=>{
        return resp.Stores;
      })
    )
  }

  findStore(storeId: string){
    const url = `${base_url}/Stores/find/${storeId}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      })
    })
  }
}


