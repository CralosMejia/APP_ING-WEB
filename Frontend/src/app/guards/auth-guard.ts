import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import { tap} from 'rxjs/operators';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private usuarioServicios:UserService,
              private router:Router
    ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot){
      return this.usuarioServicios.validateToken().pipe(
        tap(estaAutenticado => {
          if(!estaAutenticado){
            this.router.navigateByUrl('/login');
          }
        })
      );
  }
  
}
