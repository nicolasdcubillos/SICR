import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuProductoService } from '../service/menu-producto.service';
import { CategoriaService } from '../service/categoria.service';
import { SedeRestauranteService } from '../service/sede-restaurante.service';
import { ToastrService } from 'ngx-toastr';
import { ProductoService } from '../service/producto.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { PedidosService } from '../service/pedidos.service';

@Component({
  selector: 'app-sede-restaurante',
  templateUrl: './sede-restaurante.component.html',
  styleUrls: ['./sede-restaurante.component.css']
})
export class SedeRestauranteComponent {
  constructor(private route: ActivatedRoute,
    private menuProductoService: MenuProductoService,
    private categoriaService: CategoriaService,
    private pedidoService:PedidosService,
    private sedeRestauranteService: SedeRestauranteService,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService) { }

  sedeId: any;
  menuProductos: any;
  categorias: any;
  sede: any;
  isLoading: boolean = true;

  subTotalCompra:number = 0;
  totalCompra:number = 0;

  productosCarritos:any[]=[];
  productosCarritosNombreCant:any[]=[];
  realizarPedidoDTO:any={};

  showModal = false;

  ngOnInit() {
    this.spinner.show();

    this.route.paramMap.subscribe(params => {
      this.sedeId = params.get('id');
    });

    // Traer la sede.

    this.sedeRestauranteService.getById(this.sedeId).subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.sede = response.objeto;
      } else {
        this.toastr.error('No se pudo traer la información de la sede. Intente más tarde.', 'Error')
      }
    });

    // Traer categorías.

    this.categoriaService.getAll().subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.categorias = response.objeto;
      } else {
        this.toastr.error('No se pudo traer la información de las categorías. Intente más tarde.', 'Error')
      }
      this.spinner.hide();
    });

    // Traer productos de cada categoría.

    console.log(this.categorias);

    

    // Listar el menu por la sede restaurante.

    this.menuProductoService.getBySedeRestauranteId(this.sedeId).subscribe((response: any) => {
      if (response.exitosa) {
        this.menuProductos = response.objeto;
        console.log("LISTA PRODUCTO")
        console.log(this.menuProductos);
      } else {
        this.toastr.error('No se pudo traer la información del menú. Intente más tarde.', 'Error')
      }
      this.spinner.hide();
    });
  }

  confirmarCompra(){
    this.pedidoService.realizarPedido(this.realizarPedidoDTO).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          this.pedidoService.realizarPago().subscribe({
            next:(res:any)=>{
              if(res.exitosa){
                this.toastr.success(res.descripcionRespuesta, 'Success')
              }
            }
          })
        }
      }
    })
  }

  procederCompra(){
    this.totalCompra = this.subTotalCompra * 1.20;
    this.realizarPedidoDTO.subTotal = this.subTotalCompra;
    this.realizarPedidoDTO.total = this.totalCompra;
    this.realizarPedidoDTO.fecha = new Date();
    this.realizarPedidoDTO.miembro  = 0;
    this.realizarPedidoDTO.sedeRestaurante  = this.sedeId;
    this.realizarPedidoDTO.usuario  = 1;
    this.realizarPedidoDTO.pedidoProductos=this.productosCarritos;
    this.openModal();
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  agregarCarrito(producto:any){
    let productoDTO :any = {};
    const indiceEncontrado = this.productosCarritos.findIndex((elemento) => elemento.pedidoProductoId === producto.id);
    if (indiceEncontrado !== -1) {
      this.productosCarritos[indiceEncontrado].pedidoProductoCantidad += 1;
      this.productosCarritosNombreCant[indiceEncontrado].cant += 1;
      this.subTotalCompra += producto.precio;
    } else {
      productoDTO.pedidoProductoId = producto.id;
      productoDTO.pedidoProductoCantidad = 1;
      this.productosCarritos.push(productoDTO);
      this.productosCarritosNombreCant.push({cant:productoDTO.pedidoProductoCantidad, name:producto.nombre});
      this.toastr.success('Producto Agregado al carrito.', 'Success')
      this.subTotalCompra += producto.precio;
    }
  }
}
