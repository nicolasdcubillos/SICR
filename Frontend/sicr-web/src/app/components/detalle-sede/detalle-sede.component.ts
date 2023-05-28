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
  miembroEdit:any={};

  sede:any ={};
  idSedeEdit:string='';
  crearMiembroForm:FormGroup;

  tiposMiembro: any=[];
  miembros: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearMiembroForm = this.fb.group({
      nombre: ['',[Validators.required]],
      apellido: ['',[Validators.required]],
      salario: ['',[Validators.required]],
      telefono: ['',[Validators.required]],
      sedeRestaurante: [''],
      tipoMiembro: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getTiposMiembro();
    if(params['id']) {
      this.idSedeEdit=params['id'];
      this.getMiembrosSede();
      this.admonService.getSedeById(params['id']).subscribe({
        next:(res:any)=>{
          this.sede=res.objeto;
          console.log(this.sede)
        }
      })
    }
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

  cancellEdit(){
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
