import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Product } from 'src/app/models/Product';
import { ProductService } from '../product.service';

@Injectable({
  providedIn: 'root'
})
export class ProductCardsService {

  private searchParameter:string;
  private products:Product[] = [];
  private arrayAux=[]
  private products$: Subject<Product[]>;

  constructor(
    private prodSrv:ProductService
  ) { 
    this.products$= new Subject();
  }

  changeSearchParameter(search:string){
    this.searchParameter = search;


    this.prodSrv.findProducts(search).subscribe((products:Product[]) =>{
      this.products = products;
      
    })

    setTimeout(() => {
      this.products$.next(this.products);
    }, 2000);

    
  }

  getProducts$():Observable<Product[]>{
    return this.products$.asObservable();
  }
}
