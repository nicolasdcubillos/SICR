import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  
  private MS_ADMON_URL = environment.MS_ADMON_URL;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(`${this.MS_ADMON_URL}/Producto/getAll`);
  }

  getById(id: number) {
    return this.http.get(`${this.MS_ADMON_URL}/Producto/getById?id=${id}`);
  }
}
