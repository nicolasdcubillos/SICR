import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';
import * as moment from 'moment';

@Component({
  selector: 'app-gestionar-sedes',
  templateUrl: './gestionar-sedes.component.html',
  styleUrls: ['./gestionar-sedes.component.css']
})
export class GestionarSedesComponent implements OnInit {

  crearSedeForm:FormGroup;

  edit:boolean = false;

  sedeEdit:any ={};
  sedeEditSend:any={} ;

  sedes: any=[];
  restaurantes: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearSedeForm = this.fb.group({
      nombre: ['',[Validators.required]],
      direccion: ['',[Validators.required]],
      latitud: ['0'],
      longitud: ['0'],
      fechaApertura: ['',[Validators.required]],
      fechaCierre: ['',[Validators.required]],
      capacidad: ['',[Validators.required]],
      restaurante: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getSedes();
    this.getRestaurantes();
    if(params['id']) {
      this.edit=true;
      this.admonService.getSedeById(params['id']).subscribe({
        next:(res:any)=>{
          this.sedeEdit=res.objeto;
          console.log(this.sedeEdit)
          this.crearSedeForm.get('nombre')!.setValue(this.sedeEdit.nombre);
          this.crearSedeForm.get('capacidad')!.setValue(this.sedeEdit.capacidad);
          this.crearSedeForm.get('fechaApertura')!.setValue(this.sedeEdit.fechaApertura);
          this.crearSedeForm.get('fechaCierre')!.setValue(this.sedeEdit.fechaCierre);
          this.crearSedeForm.get('direccion')!.setValue(this.sedeEdit.direccion);
          this.crearSedeForm.get('restaurante')!.setValue(this.sedeEdit.restaurante);

        }
      })
    }
  }

  getRestaurantes(){
    this.admonService.getRestaurantes().subscribe({
      next:(res:any)=>{
        this.restaurantes = res.objeto;
      }
    })
  }

  getSedes(){
    this.admonService.getSedes().subscribe({
      next:(res:any)=>{
        this.sedes = res.objeto;
        console.log(res)
      }
    })
  }

  crear(){
    console.log(this.crearSedeForm.value);
    var fechaAux = moment(this.crearSedeForm.value.fechaApertura);
    var fechaAux2 = moment(this.crearSedeForm.value.fechaCierre);
    this.crearSedeForm.value.fechaApertura= new Date(fechaAux.format("YYYY-MM-DD HH:mm"))
    this.crearSedeForm.value.fechaCierre= new Date(fechaAux2.format("YYYY-MM-DD HH:mm"))
    console.log(this.crearSedeForm.value)
    this.admonService.crearSede(this.crearSedeForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Sede Creada',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
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
        window.location.reload();
      }
    })
  }

  confirmarEdicion(){
    Swal.fire({
      title: 'Esta seguro de editar esta sede ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarSede();
    })
  }

  editarSede(){
    this.sedeEdit.nombre = this.crearSedeForm.value.nombre
    this.sedeEdit.direccion = this.crearSedeForm.value.direccion
    this.sedeEdit.capacidad = this.crearSedeForm.value.capacidad
    this.sedeEdit.restaurante = this.crearSedeForm.value.restaurante
    var fechaAux = moment(this.crearSedeForm.value.fechaApertura);
    var fechaAux2 = moment(this.crearSedeForm.value.fechaCierre);
    this.sedeEdit.fechaApertura= new Date(fechaAux.format("YYYY-MM-DD HH:mm"))
    this.sedeEdit.fechaCierre= new Date(fechaAux2.format("YYYY-MM-DD HH:mm"))
    this.admonService.updateSede(this.sedeEdit,this.sedeEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Sede Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-sedes'])
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
          this.router.navigate(['/gestionar-sedes'])
        }
      }
    })
  }

  confirmarEliminar(sede:any){
    Swal.fire({
      title: 'Esta seguro de eliminar esta sede ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarSede(sede);
    })
  }

  eliminarSede(sede:any){
    this.admonService.eliminarSede(sede.id).subscribe({
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
          }).then(()=>{
            window.location.reload();
          })
        }
      }
    })
  }
}
