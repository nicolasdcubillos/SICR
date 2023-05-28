import { Component } from '@angular/core';
import { SedeRestauranteService } from '../service/sede-restaurante.service';
import { RestauranteService } from '../service/restaurante.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {
  sedes: any;
  restaurante: any;

  constructor(private sedeRestauranteService: SedeRestauranteService,
    private restauranteService: RestauranteService,
    private spinner: NgxSpinnerService) { }

  ngOnInit() {
    this.spinner.show();
    // Listar sedes restaurante.

    this.sedeRestauranteService.getAll().subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.sedes = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
      this.spinner.hide();
    });

    this.restauranteService.getById(1).subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.restaurante = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
    });
  }

}
