import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private MS_ADMON_URL = environment.MS_ADMON_URL;

  constructor(private http: HttpClient) { }

  getMenus() {
    return this.http.get(`${this.MS_ADMON_URL}/Menu/getAll`);
  }
}
