import { Component } from '@angular/core';
import { RestauranteService } from './service/restaurante.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  sedes: any;
  restaurante: any;

  constructor(private restauranteService: RestauranteService) { }

  ngOnInit() {
    // Traer restaurante principal - id 1.

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