import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class SedeRestauranteService {

  private MS_ADMON_URL = environment.MS_ADMON_URL;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(`${this.MS_ADMON_URL}/SedeRestaurante/getAll`);
  }
}
