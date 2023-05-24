import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  endpoint:string= environment.API_URI.ms_seguridad;
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient, public router: Router) { }

  //Login
  login(user:any){
    return this.http
    .post(`${this.endpoint}/IniciarSesion`,user)
    .subscribe({
      next:(res:any)=>{
        localStorage.clear();
        localStorage.setItem('sessionToken',res.objeto.token);
      }
    })
  }
}
