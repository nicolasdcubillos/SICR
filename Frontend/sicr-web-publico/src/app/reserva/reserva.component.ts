import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ReservaService } from '../service/reserva.service';
import {ActivatedRoute} from "@angular/router";
import {SedeRestauranteService} from "../service/sede-restaurante.service"; // Reemplaza 'path-to-your-reserva-service' con la ubicación real de tu servicio de reserva
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reserva',
  templateUrl: './reserva.component.html',
  styleUrls: ['./reserva.component.css']
})
export class ReservaComponent {
  constructor(private route: ActivatedRoute,
              private reservaService: ReservaService,
              private sedeRestauranteService: SedeRestauranteService,
              private router: Router,
              private toastr: ToastrService) {}

  sedes: any;
  asientos: any;
  fecha: any; // Asegúrate de que coincida con el formato de fecha esperado por el backend
  horas: any;
  usuario: any;
  sedeId:any;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.sedeId = params.get('idSede');
    });

    this.sedeRestauranteService.getAll().subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.sedes = response.objeto;
      } else {
        this.toastr.error('No se pudo traer la información de la sede. Intente más tarde.', 'Error')
      }
    });
  }

  crearReserva() {
    const reservaData = {
      asientos: this.asientos,
      fecha: new Date(this.fecha).toISOString(), // Convierte la fecha a formato ISO
      horas: this.horas,
      usuario: 1,
      sedeRestaurante: this.sedeId
    };
    
    const currentDate = new Date().toISOString();
    const currentDateTimePlusOneHour = new Date();
    currentDateTimePlusOneHour.setHours(currentDateTimePlusOneHour.getHours() + 1);

    if(this.asientos <= 20){
      if(reservaData.fecha > currentDate){
        if (reservaData.fecha > currentDateTimePlusOneHour.toISOString()){
          if (reservaData.horas <= 8){
            this.reservaService.crearReserva(reservaData).subscribe((response: any) => {
              if (response.exitosa) {
                if (this.toastr.success('Reserva creada exitosamente.', 'Reserva confirmada')) {
                  this.router.navigate(['/sede', this.sedeId]);
                }
              } else {
                this.toastr.error(response.descripcionRespuesta, 'Error');
              }
            });
          } else {
            this.toastr.error('No se pueden hacer reservas con duración de más de 8 horas en línea.', 'Error');
          }
        } else {
          this.toastr.error('La hora seleccionada debe ser al menos 1 hora después de la hora actual.', 'Error');
        }
      } else {
        this.toastr.error('La fecha seleccionada debe ser mayor o igual a la fecha actual.', 'Error');
      }
    } else {
      this.toastr.error('No se pueden hacer reservas para más de 20 personas en línea.', 'Error');
    }
  }
}
