import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { User } from '../models/User.model';
import { catchError, map, tap } from 'rxjs/operators';
import { LoginForm } from '../Interfaces/login-interface';
import { of } from 'rxjs';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  get token():string{

    return localStorage.getItem('token') || '';

  }

  get headers(){

    return new HttpHeaders().set('Content-Type', 'application/json').append('token',this.token);

  }


  saveLocalStorage(token:string){
    localStorage.setItem('token',token);
  }

  getUsers(){
    const url = `${base_url}/Users/entry`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      })
    })
  }



  createUser(formData: any){
    const user: User =formData;
    return this.http.post(`${base_url}/Users/create`,user).pipe(
      tap((resp: any) => {
        this.saveLocalStorage(resp.token);
      })
  );
  }

  deleteUser(id:String, userLoginID: string){
    const url = `${base_url}/Users/delete/${id}`;
    return this.http.delete(url,{
      headers: this.headers,
      body:userLoginID
   });
  }

  updateUser(user:User,userLoginID: string){
    const url = `${base_url}/Users/update/${user.id}`;
    return this.http.put(url,{
        "user":user,
        "id":userLoginID
    },{
      headers: {
        'token':this.token
      }
    });
  }

  login(email:string,name:string){
    const url = `${base_url}/login`;

    return this.http.post(url,{email,name},{
      headers: new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      }),
      responseType: 'text'

    }).pipe(
      tap((resp: any) => {
        this.saveLocalStorage(JSON.parse(resp).token);
      }),
       map((resp:any) =>{
           return JSON.parse(resp);
       })
    );
  }

  logout(){
    localStorage.removeItem('token');
  }

  validateToken(){
    const url = `${base_url}/login/validate`;
    return this.http.get(url,{
      headers:this.headers
    }).pipe(
      map((resp: any )=>{
        return resp.Validation;
      }),
      catchError(error => {
        return of(false)
      } )
    )
  }

  getUserRol(userLoginID: string){
    const url = `${base_url}/Users/rol/${userLoginID}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      }),

    })
  }

  findUser(userLoginID: string){
    const url = `${base_url}/Users/find/${userLoginID}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      }),

    })
  }

  addProductToShoppingCar(userId:string,productId:string, amount:number ){


    return this.http.post(`${base_url}/Users/addprod`,{
      'userId':userId,
      'productId':productId,
      'amount':amount
    });
  }

  deleteProductToShoppingCar(userId:string,productId:string, amount:number ){
    return this.http.delete(`${base_url}/Users/deleteprod`,{
      headers: this.headers,
      body:{
        'userId':userId,
        'productId':productId,
        'amount':amount
      }
   });
  }

  getShoppingCar(userLoginID: string){
    const url = `${base_url}/Users/shopping/${userLoginID}`;
    return this.http.get(url,{
      headers:new HttpHeaders({
        'token' : this.token,
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : "*"
      }),

    })
  }

  buy(userId:string){
    return this.http.post(`${base_url}/Users/buy`,userId);
  }


}


