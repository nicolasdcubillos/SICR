import { Injectable } from '@angular/core';
import { MenuService } from './menu.service';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MenuProductoService {
  private MS_ADMON_URL = environment.MS_ADMON_URL;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(`${this.MS_ADMON_URL}/MenuProducto/getAll`);
  }

  getBySedeRestauranteId(id: number) {
    return this.http.get(`${this.MS_ADMON_URL}/MenuProducto/getBySedeRestauranteId?id=${id}`);
  }
}
