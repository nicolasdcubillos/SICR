import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gestionar-menu',
  templateUrl: './gestionar-menu.component.html',
  styleUrls: ['./gestionar-menu.component.css']
})
export class GestionarMenuComponent implements OnInit{
 
  crearMenuForm:FormGroup;
  crearMenuProductoForm:FormGroup;

  edit:boolean = false;

  menuEdit:any ;

  menus: any=[];
  productos: any=[];
  sedes: any=[];

  productosMenu:any=[];
  showModal = false;
  idMenuEdit:string='';

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearMenuForm = this.fb.group({
      nombre: ['',[Validators.required]],
      descripcion: ['',[Validators.required]],
      sedeRestaurante: ['',[Validators.required]],
    });
    this.crearMenuProductoForm = this.fb.group({
      menu: [''],
      producto: ['']
    })

  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getMenus();
    this.getSedes();
    this.getProductos();
    if(params['id']) {
      this.edit=true;
      this.idMenuEdit=params['id'];
      this.admonService.getMenuById(params['id']).subscribe({
        next:(res:any)=>{
          this.menuEdit=res.objeto;
          console.log(this.menuEdit)
          this.crearMenuForm.get('nombre')!.setValue(this.menuEdit.nombre);
          this.crearMenuForm.get('descripcion')!.setValue(this.menuEdit.descripcion);
          this.crearMenuForm.get('sedeRestaurante')!.setValue(this.menuEdit.sedeRestaurante);
          this.admonService.getProductosByMenuId(Number(params['id']),this.menuEdit).subscribe({
            next:(res:any)=>{
              this.productosMenu=res.objeto;
              console.log(this.productosMenu);
            }
          })
        }
      })
    }
  }

  addSelectedProduct(): void {
    this.crearMenuProductoForm.value.menu=Number(this.idMenuEdit)
    this.admonService.crearMenuProducto(this.crearMenuProductoForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Producto de Menú Creado',
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
    this.crearMenuProductoForm.reset();
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

  getProductos(){
    this.admonService.getProductos().subscribe({
      next:(res:any)=>{
        this.productos = res.objeto;
      }
    })
  }

  getSedes(){
    this.admonService.getSedes().subscribe({
      next:(res:any)=>{
        this.sedes = res.objeto;
      }
    })
  }

  getMenus(){
    this.admonService.getMenus().subscribe({
      next:(res:any)=>{
        this.menus = res.objeto;
        console.log(this.menus);
      }
    })
  }

  crear(){
    this.crearMenuForm.get('sedeRestaurante')!.setValue(Number(this.crearMenuForm.value.sedeRestaurante));
    console.log(this.crearMenuForm.value)
    this.admonService.crearMenu(this.crearMenuForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Menu Creado',
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
    this.menuEdit.nombre = this.crearMenuForm.value.nombre
    this.menuEdit.categoria = Number(this.crearMenuForm.value.categoria)
    this.menuEdit.estadoProducto = Number(this.crearMenuForm.value.estadoProducto)

    console.log(this.menuEdit);

    this.admonService.updateProducto(this.menuEdit,this.menuEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Producto Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-menus'])
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
            this.router.navigate(['/gestionar-menus'])
          })
        }
      }
    })
  }

  confirmarEliminar(menu:any){
    Swal.fire({
      title: 'Esta seguro de eliminar este menú ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarMenu(menu);
    })
  }

  eliminarMenu(menu:any){
    this.admonService.eliminarMenu(menu.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Menú Eliminado',
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
