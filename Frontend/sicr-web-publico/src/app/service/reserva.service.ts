import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private MS_RESERVAS_URL = environment.MS_RESERVAS_URL;

  constructor(private http: HttpClient) { }

  crearReserva(reservaData: any): Observable<any> {
    return this.http.post(`${this.MS_RESERVAS_URL}/Reserva/crear`, reservaData);
  }
}
