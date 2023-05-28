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

  selectedOption: any;
  selectedItems: string[] = [];

  addSelectedItem(): void {
    console.log(this.crearProductoItemForm.value);
    // if (this.selectedOption) {
    //   this.selectedItems.push(this.selectedOption);
    //   this.selectedOption = '';
    // }
  }

  eliminarItemSelec(){

  }

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearProductoForm = this.fb.group({
      nombre: ['',[Validators.required]],
      categoria: ['',[Validators.required]],
      estadoProducto: ['',[Validators.required]],
    });
    this.crearProductoItemForm = this.fb.group({
      ItemId: [''],
      ProductoId: [''],
      Cantidad: [''],
      UnidadMedida: [''],
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
      this.admonService.getProductoById(params['id']).subscribe({
        next:(res:any)=>{
          this.productoEdit=res.objeto;
          console.log(this.productoEdit)
          this.crearProductoForm.get('nombre')!.setValue(this.productoEdit.nombre);
          this.crearProductoForm.get('categoria')!.setValue(this.productoEdit.categoria);
          this.crearProductoForm.get('estadoProducto')!.setValue(this.productoEdit.estadoProducto);
        }
      })
    }
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
      title: 'Esta seguro de editar este usuario ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarUsuario();
    })
  }

  editarUsuario(){
    this.productoEdit.nombres = this.crearProductoForm.value.nombres
    this.productoEdit.apellidos = this.crearProductoForm.value.apellidos
    this.productoEdit.username = this.crearProductoForm.value.username
    this.productoEdit.direccion = this.crearProductoForm.value.direccion
    this.productoEdit.telefono = this.crearProductoForm.value.telefono
    this.productoEdit.email = this.crearProductoForm.value.email
    this.productoEdit.tipoUsuario = this.crearProductoForm.value.tipoUsuario
    this.productoEdit.password = this.crearProductoForm.value.password

    console.log(this.productoEdit);

    this.admonService.updateUsaurios(this.productoEdit,this.productoEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Editado',
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
