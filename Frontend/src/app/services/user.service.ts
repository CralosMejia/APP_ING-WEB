import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { User } from '../models/User.model';
import { map, tap } from 'rxjs/operators';
import { LoginForm } from '../Interfaces/login-interface';

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

  getUsers(){

    const url = `${base_url}/Users`;
    return this.http.get(url,{
      headers: this.headers
   });
  }

  saveLocalStorage(token:string){
    localStorage.setItem('token',token);
  }

  createUser(formData: any){
    const user: User =formData;
    return this.http.post(`${base_url}/Users`,user).pipe(
      tap((resp: any) => {
        this.saveLocalStorage(resp.token);
      })
  );
  }

  deleteUser(id:String){
    const url = `${base_url}/Users/${id}`;
    return this.http.delete(url,{
      headers: this.headers,
      responseType: 'text' 
   });
  }

  updateUser(user:User){
    const url = `${base_url}/Users/${user.id}`;
    return this.http.put(url,user,{
      headers: this.headers,
      responseType: 'text' 
    });
  }

  login(formData: any){
    const user:LoginForm =formData;
    const url = `${base_url}/login`;

    return this.http.post(url,user,{
      headers: this.headers,
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
}
