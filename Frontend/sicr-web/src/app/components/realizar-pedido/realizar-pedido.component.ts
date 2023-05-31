import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-realizar-pedido',
  templateUrl: './realizar-pedido.component.html',
  styleUrls: ['./realizar-pedido.component.css']
})
export class RealizarPedidoComponent implements OnInit {

  crearPedidoForm:FormGroup;
  crearProductoPedidoForm:FormGroup;

  sedes: any=[];
  productosPedido: any[]=[];
  productos: any=[];

  subtotal: number=0;
  total: number=0;

  user:any={};


  constructor(public fb: FormBuilder,private router:Router,private admonService:AdmonService){
    this.crearPedidoForm = this.fb.group({
      fecha: [''],
      miembro: [''],
      pedidoProductos: [[],],
      sedeRestaurante: ['',[Validators.required]],
      subtotal: [''],
      total: [''],
      usuario: ['',],
    });
    this.crearProductoPedidoForm = this.fb.group({
      pedidoProductoId: ['',[Validators.required]],
      cantidad: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!);
    this.getSedes();
    this.getProductos();
  }

  getSedes(){
    this.admonService.getSedes().subscribe({
      next:(res:any)=>{
        this.sedes = res.objeto;
      }
    })
  }

  getProductos(){
    this.admonService.getProductos().subscribe({
      next:(res:any)=>{
        this.productos = res.objeto;
      }
    })
  }

  confirmDeleteItemProd(prodId:string){
    let producto = this.productos.find((objeto:any) => objeto.id === Number(prodId));
    this.subtotal -= producto.precio * producto.cantidad; 
    this.total -= (producto.precio * producto.cantidad)*1.20; 
    this.productosPedido = this.productosPedido.filter((objeto) => objeto.id !== prodId);
  }

  addSelectedProduct(){
    console.log(this.crearProductoPedidoForm.value);
    let producto = this.productos.find((objeto:any) => objeto.id === Number(this.crearProductoPedidoForm.value.pedidoProductoId));
    producto.cantidad = this.crearProductoPedidoForm.value.cantidad;
    this.subtotal += producto.precio * producto.cantidad; 
    this.total += (producto.precio * producto.cantidad)*1.20; 
    this.productosPedido.push(producto);
    this.crearProductoPedidoForm.reset();
  }



  crear(){
    this.crearPedidoForm.value.fecha = new Date();
    this.crearPedidoForm.value.miembro = 1;
    this.crearPedidoForm.value.usuario = Number(this.user.usuarioId);
    this.crearPedidoForm.value.sedeRestaurante = Number(this.crearPedidoForm.value.sedeRestaurante);
    this.crearPedidoForm.value.total = this.total;
    this.crearPedidoForm.value.subtotal = this.subtotal;
    console.log(this.productosPedido);
    for(let i=0;i<this.productosPedido.length;i++){
      let productoPedido:any={};
      productoPedido.pedidoProductoCantidad = this.productosPedido[i].cantidad;
      productoPedido.pedidoProductoId = this.productosPedido[i].id;
      this.crearPedidoForm.value.pedidoProductos.push(productoPedido);
    }
    console.log(this.crearPedidoForm.value);
    this.admonService.realizarPedido(this.crearPedidoForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Pedido Creado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            window.location.reload();
          })
        }else{
          Swal.fire({
            title: 'Error',
            icon: 'error',
            text: res.descripcionRespuesta,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            window.location.reload();
          })
        }
      }
    })
  }

  logout(){
    localStorage.removeItem('sessionToken');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
