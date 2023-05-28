import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-gestionar-producto',
  templateUrl: './gestionar-producto.component.html',
  styleUrls: ['./gestionar-producto.component.css']
})
export class GestionarProductoComponent implements OnInit {

  crearProductoForm:FormGroup;
  crearProductoItemForm:FormGroup;

  edit:boolean = false;

  productoEdit:any ;
  prodcutoEditSend:any={} ;

  productos: any=[];
  items: any=[];
  categorias: any=[];
  estadosProducto: any=[];

  itemsProducto:any=[];
  showModal = false;
  idProductoEdit:string='';

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearProductoForm = this.fb.group({
      nombre: ['',[Validators.required]],
      categoria: ['',[Validators.required]],
      estadoProducto: ['',[Validators.required]],
    });
    this.crearProductoItemForm = this.fb.group({
      item: [''],
      producto: [''],
      cantidad: [''],
      unidadMedida: [''],
    })

  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getProductos();
    this.getCategoria();
    this.getEstadoProducto();
    this.getItems();
    if(params['id']) {
      this.edit=true;
      this.idProductoEdit=params['id'];
      this.admonService.getProductoById(params['id']).subscribe({
        next:(res:any)=>{
          this.productoEdit=res.objeto;
          console.log(this.productoEdit)
          this.crearProductoForm.get('nombre')!.setValue(this.productoEdit.nombre);
          this.crearProductoForm.get('categoria')!.setValue(this.productoEdit.categoria);
          this.crearProductoForm.get('estadoProducto')!.setValue(this.productoEdit.estadoProducto);
          this.admonService.getItemsByProductId(Number(params['id']),this.productoEdit).subscribe({
            next:(res:any)=>{
              this.itemsProducto=res.objeto;
              console.log(this.itemsProducto);
            }
          })
        }
      })
    }
  }

  addSelectedItem(): void {
    this.crearProductoItemForm.value.producto=Number(this.idProductoEdit)
    this.admonService.crearProductoItem(this.crearProductoItemForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Item de Producto Creado',
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
            text: res.descripcionExcepcion,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            window.location.reload();
          })
        }
      }
    })
    this.closeModal();
    this.crearProductoItemForm.reset();
  }

  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  confirmDeleteItemProd(itemPrductoId:string){
    Swal.fire({
      title: 'Seguro quiere eliminar el item del producto ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.deleteProductoItem(itemPrductoId);
    })
  }

  deleteProductoItem(itemPrductoId:string){
    this.admonService.eliminarProductoItem(itemPrductoId).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Item del producto Eliminado',
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
            text: res.descripcionExcepcion,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
        }
      }
    })
  }

  getItems(){
    this.admonService.getItems().subscribe({
      next:(res:any)=>{
        this.items = res.objeto;
      }
    })
  }

  getCategoria(){
    this.admonService.getCategorias().subscribe({
      next:(res:any)=>{
        this.categorias = res.objeto;
      }
    })
  }

  getEstadoProducto(){
    this.admonService.getEstadosProducto().subscribe({
      next:(res:any)=>{
        this.estadosProducto = res.objeto;
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

  crear(){
    this.crearProductoForm.get('categoria')!.setValue(Number(this.crearProductoForm.value.categoria));
    this.crearProductoForm.get('estadoProducto')!.setValue(Number(this.crearProductoForm.value.estadoProducto));
    console.log(this.crearProductoForm.value)
    this.admonService.crearProducto(this.crearProductoForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Producto Creado',
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
            text: res.descripcionExcepcion,
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

  confirmarEdicion(){
    Swal.fire({
      title: 'Esta seguro de editar este producto ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarProducto();
    })
  }

  editarProducto(){
    this.productoEdit.nombre = this.crearProductoForm.value.nombre
    this.productoEdit.categoria = Number(this.crearProductoForm.value.categoria)
    this.productoEdit.estadoProducto = Number(this.crearProductoForm.value.estadoProducto)

    console.log(this.productoEdit);

    this.admonService.updateProducto(this.productoEdit,this.productoEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Producto Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-productos'])
          })
        }else{
          Swal.fire({
            title: 'Error',
            icon: 'error',
            text: res.descripcionExcepcion,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-productos'])
          })
        }
      }
    })
  }

  confirmarEliminar(user:any){
    Swal.fire({
      title: 'Esta seguro de eliminar este usuario ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarUsuario(user);
    })
  }

  eliminarUsuario(user:any){
    this.admonService.eliminarUsuario(user.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Eliminado',
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
            text: res.descripcionExcepcion,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
        }
      }
    })
  }
}
