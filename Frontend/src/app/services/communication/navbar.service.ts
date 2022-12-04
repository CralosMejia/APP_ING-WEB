import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  private searchParameter:string;
  private searchParameter$: Subject<string>;

  constructor() { 
    this.searchParameter$= new Subject();
  }

  changeSearchParameter(search:string){
    this.searchParameter = search;
    this.searchParameter$.next(this.searchParameter);
  }

  getSearchParameter$():Observable<string>{
    return this.searchParameter$.asObservable();
  }
}
