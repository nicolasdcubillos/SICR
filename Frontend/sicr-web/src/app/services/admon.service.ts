import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdmonService {
  endpoint:string= environment.API_URI.ms_admon;
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }


  //Usuarios
  getUsaurios(){
    return this.http.get(`${this.endpoint}/Usuario/getAll`);
  }

  getUsauriosById(userId:string){
    let api = `${this.endpoint}/Usuario/getById`;
    let params = new HttpParams();
    params = params.append('id', userId);
    return this.http.get(api,{params: params});
  }

  updateUsaurios(user:any){
    let api = `${this.endpoint}/Usuario/actualizar`;
    return this.http.post(api,user);
  }

  crearUsaurio(user:any){
    let api = `${this.endpoint}/Usuario/crear`
    return this.http.post(api,user);
  }

  //Tipo Usuarios
  getTipoUsuario(){
    return this.http.get(`${this.endpoint}/TipoUsuario/getAll`);
  }

  //Menu
  getMenu(){
    return this.http.get(`${this.endpoint}/Menu/getAll`);
  }

  getMenuById(menuId:string){
    let api = `${this.endpoint}/Menu/getById`;
    let params = new HttpParams();
    params = params.append('id', menuId);
    return this.http.get(api,{params: params});
  }

  updateMenu(menu:any){
    let api = `${this.endpoint}/Menu/actualizar`;
    return this.http.post(api,menu);
  }

  crearMenu(menu:any){
    let api = `${this.endpoint}/Menu/crear`
    return this.http.post(api,menu);
  }

}
