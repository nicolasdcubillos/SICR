import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-sede',
  templateUrl: './detalle-sede.component.html',
  styleUrls: ['./detalle-sede.component.css']
})
export class DetalleSedeComponent implements OnInit {

  editMiembro:boolean = false;
  editItemSede:boolean = false;
  miembroEdit:any={};
  itemSedeEdit:any={};

  sede:any ={};
  idSedeEdit:string='';
  crearMiembroForm:FormGroup;
  crearItemSedeForm:FormGroup;
  crearSolicitudInventarioForm:FormGroup;
  

  tiposMiembro: any=[];
  miembros: any=[];
  itemsSede:any=[];
  items:any=[];
  llegadaInventario:any=[];
  salidaInventario:any=[];
  reservas:any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearMiembroForm = this.fb.group({
      nombre: ['',[Validators.required]],
      apellido: ['',[Validators.required]],
      salario: ['',[Validators.required]],
      telefono: ['',[Validators.required]],
      sedeRestaurante: [''],
      tipoMiembro: ['',[Validators.required]],
    });
    this.crearItemSedeForm = this.fb.group({
      cantidad: ['',[Validators.required]],
      item: ['',[Validators.required]],
      sedeRestaurante: [''],
    })
    this.crearSolicitudInventarioForm = this.fb.group({
      restauranteId: [''],
      sedeOrigenId: [''],
      itemId: ['',[Validators.required]],
      cantidad: ['',[Validators.required]],
    })
  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getTiposMiembro();
    this.getItems();
    if(params['id']) {
      this.idSedeEdit=params['id'];
      this.getMiembrosSede();
      this.getItemsSede();
      this.getLlegadaInventario();
      this.getSalidaInventario();
      this.getReservas();
      this.admonService.getSedeById(params['id']).subscribe({
        next:(res:any)=>{
          this.sede=res.objeto;
          console.log(this.sede)
        }
      })
    }
  }

  //Reservas
  getReservas(){
    this.admonService.getReservasBySedeId(this.idSedeEdit).subscribe({
      next:(res:any)=>{
        this.reservas = res.objeto;
      }
    })
  }

  //Inventario
  getLlegadaInventario(){
    this.admonService.solicitudesInventarioBySedeId(Number(this.idSedeEdit),false).subscribe({
      next:(res:any)=>{
        this.llegadaInventario = res.objeto;
        console.log(this.llegadaInventario)
      }
    })
  }
  getSalidaInventario(){
    this.admonService.solicitudesInventarioBySedeId(Number(this.idSedeEdit),true).subscribe({
      next:(res:any)=>{
        this.salidaInventario = res.objeto;
        console.log(this.salidaInventario)
      }
    })
  }

  confirmarLlegadaInventario(transfer:any){
    this.admonService.actualizarEstadoTransferencia(transfer.idSolicitud,transfer).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Estado de la transferencia actualizado',
            icon: 'success',
            text:  res.descripcionRespuesta,
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

  //SolicitarInventario
  solicitarInventario(){
    this.crearSolicitudInventarioForm.get('sedeOrigenId')!.setValue(Number(this.idSedeEdit));
    this.crearSolicitudInventarioForm.get('restauranteId')!.setValue(1);
    this.crearSolicitudInventarioForm.get('itemId')!.setValue(Number(this.crearSolicitudInventarioForm.value.itemId));
    console.log(this.crearSolicitudInventarioForm.value);
    this.admonService.solicitarInventario(this.crearSolicitudInventarioForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Solicitud de inventario Creada',
            icon: 'success',
            text:'La sede de '+res.objeto.nombreSedeRestaurante+' realizara el envio',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            //window.location.reload();
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

  cancelarSolicitudInventario(){
    this.crearSolicitudInventarioForm.reset();
  }

  //ItemSede
  getItemsSede(){
    this.admonService.getItemsBySedeId(this.idSedeEdit).subscribe({
      next:(res:any)=>{
        this.itemsSede=res.objeto;
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

  crearItemSede(){
    this.crearItemSedeForm.get('item')!.setValue(Number(this.crearItemSedeForm.value.item));
    this.crearItemSedeForm.get('sedeRestaurante')!.setValue(Number(this.idSedeEdit));
    console.log(this.crearItemSedeForm.value)
    this.admonService.crearItemSede(this.crearItemSedeForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Item de la Sede Creado',
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

  confirmarEdicionItemSede(){
    Swal.fire({
      title: 'Esta seguro de editar este Item de la Sede ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarItemSede();
    })
  }

  editarItemSede(){
    this.itemSedeEdit.cantidad = this.crearItemSedeForm.value.cantidad
    this.itemSedeEdit.item = Number(this.crearItemSedeForm.value.item)
    this.admonService.updateItemSede(this.itemSedeEdit,this.itemSedeEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Item de la sede Editado',
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

  cancellEditItemSede(){
    this.editMiembro=false;
    this.crearItemSedeForm.reset();
  }

  buscarItemSedeEdit(itemSede:any){
    this.admonService.getItemSedeById(itemSede.id).subscribe({
      next:(res:any)=>{
        this.itemSedeEdit=res.objeto;
        console.log(this.itemSedeEdit)
        this.editItemSede=true;
        this.crearItemSedeForm.get('cantidad')!.setValue(this.itemSedeEdit.cantidad);
        this.crearItemSedeForm.get('item')!.setValue(this.itemSedeEdit.item);
      }
    })
  }

  confirmarEliminarItemSede(itemSede:any){
    Swal.fire({
      title: 'Esta seguro de eliminar este Item de la sede ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarItemSede(itemSede);
    })
  }

  eliminarItemSede(itemSede:any){
    this.admonService.eliminarItemSede(itemSede.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Item de la Sede Eliminado',
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

  //Miembros

  crearMiembro(){
    this.crearMiembroForm.get('sedeRestaurante')!.setValue(Number(this.idSedeEdit));
    this.crearMiembroForm.get('tipoMiembro')!.setValue(Number(this.crearMiembroForm.value.tipoMiembro));
    console.log(this.crearMiembroForm.value)
    this.admonService.crearMiembro(this.crearMiembroForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Miembro Creado',
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

  cancellEditMiembro(){
    this.editMiembro=false;
    this.crearMiembroForm.reset();
  }

  buscarMiembroEdit(miembro:any){
    this.admonService.getMiembroById(miembro.id).subscribe({
      next:(res:any)=>{
        this.miembroEdit=res.objeto;
        console.log(this.miembroEdit)
        this.editMiembro=true;
        this.crearMiembroForm.get('nombre')!.setValue(this.miembroEdit.nombre);
        this.crearMiembroForm.get('apellido')!.setValue(this.miembroEdit.apellido);
        this.crearMiembroForm.get('salario')!.setValue(this.miembroEdit.salario);
        this.crearMiembroForm.get('telefono')!.setValue(this.miembroEdit.telefono);
        this.crearMiembroForm.get('sedeRestaurante')!.setValue(this.miembroEdit.sedeRestaurante);
        this.crearMiembroForm.get('tipoMiembro')!.setValue(this.miembroEdit.tipoMiembro);
      }
    })
  }

  confirmarEdicionMiembro(){
    Swal.fire({
      title: 'Esta seguro de editar este miembro ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarMiembro();
    })
  }

  editarMiembro(){
    this.miembroEdit.nombre = this.crearMiembroForm.value.nombre
    this.miembroEdit.apellido = this.crearMiembroForm.value.apellido
    this.miembroEdit.salario = this.crearMiembroForm.value.salario
    this.miembroEdit.telefono = this.crearMiembroForm.value.telefono
    this.miembroEdit.tipoMiembro = Number(this.crearMiembroForm.value.tipoMiembro)
    this.admonService.updateMiembro(this.miembroEdit,this.miembroEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Miembro Editado',
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

  confirmarEliminarMiembro(miembro:any){
    Swal.fire({
      title: 'Esta seguro de eliminar este miembro ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarMiembro(miembro);
    })
  }

  eliminarMiembro(miembro:any){
    this.admonService.eliminarMiembro(miembro.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Miembro Eliminado',
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

  getMiembrosSede(){
    this.admonService.getMiembroBySedeId(this.idSedeEdit).subscribe({
      next:(res:any)=>{
        this.miembros=res.objeto;
        console.log(res);
      }
    })
  }

  getTiposMiembro(){
    this.admonService.getTipoMiembros().subscribe({
      next:(res:any)=>{
        this.tiposMiembro=res.objeto;
      }
    })
  }

}
