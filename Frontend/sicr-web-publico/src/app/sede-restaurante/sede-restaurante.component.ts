import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuProductoService } from '../service/menu-producto.service';
import { CategoriaService } from '../service/categoria.service';
import { SedeRestauranteService } from '../service/sede-restaurante.service';

@Component({
  selector: 'app-sede-restaurante',
  templateUrl: './sede-restaurante.component.html',
  styleUrls: ['./sede-restaurante.component.css']
})
export class SedeRestauranteComponent {
  constructor(private route: ActivatedRoute,
    private menuProductoService: MenuProductoService,
    private categoriaService: CategoriaService,
    private sedeRestauranteService: SedeRestauranteService) { }

  sedeId: any;
  menuProductos: any;
  categorias: any;
  sede: any;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.sedeId = params.get('id');
    });

    // Traer la sede.

    this.sedeRestauranteService.getById(this.sedeId).subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.sede = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
    });

    // Traer categorías.

    this.categoriaService.getAll().subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.categorias = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
    });

    // Listar el menu por la sede restaurante.

    this.menuProductoService.getBySedeRestauranteId(this.sedeId).subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.menuProductos = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
    });
  }
}
