import { Component } from '@angular/core';
import { SedeRestauranteService } from '../service/sede-restaurante.service';
import { RestauranteService } from '../service/restaurante.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {
  sedes: any;
  restaurante: any;

  constructor(private sedeRestauranteService: SedeRestauranteService,
    private restauranteService: RestauranteService) { }

  ngOnInit() {
    // Listar sedes restaurante.

    this.sedeRestauranteService.getAll().subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.sedes = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
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
