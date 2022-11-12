import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Product } from '../models/Product';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  get token():string{
   
    return localStorage.getItem('token') || '';

  }

  get headers(){
    
    return new HttpHeaders().set('Content-Type', 'application/json').append('token',this.token);
    
  }


  getProducts(userLoginID: string){
    const url = `${base_url}/Produtcs/entry/${userLoginID}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      })
    })
  }



  createProduct(formData: any,userLoginID: string){
    const product: Product =formData;
    return this.http.post(`${base_url}/Produtcs/create`,{
        "product":product,
        "id":userLoginID
    }).pipe(
      tap((resp: any) => {
      })
  );
  }

  deleteProd(id:String, userLoginID: string){
    const url = `${base_url}/Produtcs/delete/${id}`;
    return this.http.delete(url,{
      headers: this.headers,
      body:userLoginID
   });
  }

  updateProduct(prod:Product,userLoginID: string){
    const url = `${base_url}/Produtcs/update/${prod.id}`;
    return this.http.put(url,{
        "product":prod,
        "id":userLoginID
    },{
      headers: {
        'token':this.token
      }
    });
  }
}


