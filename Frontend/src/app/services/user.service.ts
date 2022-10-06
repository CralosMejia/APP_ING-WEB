import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { User } from '../models/User.model';
import { map } from 'rxjs/operators';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }


  get headers(){
    return new HttpHeaders().set('Content-Type', 'application/json')
    
  }

  getUsers(){

    const url = `${base_url}/Users`;
    return this.http.get<User[]>(url);
  }

  createUser(formData: any){
    const user: User =formData;
    return this.http.post(`${base_url}/Users`,user);
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
}
