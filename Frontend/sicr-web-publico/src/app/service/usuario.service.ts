import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private MS_ADMON_URL = environment.MS_ADMON_URL;

  constructor(private http: HttpClient) { }

  crearUsaurio(user:any){
    let api = `${this.MS_ADMON_URL}/Usuario/crear`
    return this.http.post(api,user);
  }
}
