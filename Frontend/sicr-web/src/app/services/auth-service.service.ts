import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

//Exteral imports
import Swal from 'sweetalert2';

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
        if(res.exitosa != false){
          localStorage.clear();
          console.log(res);
          localStorage.setItem('sessionToken',res.objeto.token);
          localStorage.setItem('user',JSON.stringify(res.objeto));
          Swal.fire({
            title: 'Bienvenido a SICR '+res.objeto.rolNombre,
            icon: 'success',
            text: res.objeto.nombres +' ' + res.objeto.apellidos,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
          .then((result)=>{
            if(res.objeto.rolId=='1')this.router.navigate(['/home-admin']);
            if(res.objeto.rolId=='3')this.router.navigate(['/realizar-pedido']);
          });
        }else{
          Swal.fire({
            title: 'Error en la autenticación',
            icon: 'error',
            text: res.descripcionRespuesta,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
        }
      }
    })
  }

  getToken() {
    return localStorage.getItem('sessionToken');
  }
}
