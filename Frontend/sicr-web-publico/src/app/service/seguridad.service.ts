import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class SeguridadService {

  endpoint:string = environment.MS_SEGURIDAD_URL;

  constructor(private http: HttpClient,public router: Router) { }

  
  login(user:any){
    return this.http
    .post(`${this.endpoint}/IniciarSesion`,user)
    .subscribe({
      next:(res:any)=>{
        localStorage.clear();
        console.log(res);
        localStorage.setItem('sessionToken',res.objeto.token);
        localStorage.setItem('user',JSON.stringify(res.objeto));
      }
    })
  }

  getToken() {
    return localStorage.getItem('sessionToken');
  }
}
