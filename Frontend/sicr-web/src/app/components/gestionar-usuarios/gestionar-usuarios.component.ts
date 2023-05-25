import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gestionar-usuarios',
  templateUrl: './gestionar-usuarios.component.html',
  styleUrls: ['./gestionar-usuarios.component.css']
})
export class GestionarUsuariosComponent implements OnInit{

  crearUsuarioForm:FormGroup;

  edit:boolean = false;

  usuarios: any=[];
  tipoUsuarios: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearUsuarioForm = this.fb.group({
      nombres: ['',[Validators.required]],
      apellidos: ['',[Validators.required]],
      username: ['',[Validators.required]],
      telefono: ['',[Validators.required]],
      email: ['',[Validators.required]],
      direccion: ['',[Validators.required]],
      password: ['',[Validators.required]],
      latitud: ['0'],
      longitud: ['0'],
      tipoUsuario: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    this.getUsuarios();
    this.getTipoUsaurios();
  }

  getTipoUsaurios(){
    this.admonService.getTipoUsuario().subscribe({
      next:(res:any)=>{
        this.tipoUsuarios = res.objeto;
        console.log(this.tipoUsuarios)
      }
    })
  }

  getUsuarios(){
    this.admonService.getUsaurios().subscribe({
      next:(res:any)=>{
        console.log(res);
        this.usuarios = res.objeto;
      }
    })
  }

  crear(){
    console.log(this.crearUsuarioForm.value);
    this.admonService.crearUsaurio(this.crearUsuarioForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Creado',
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
        //window.location.reload();
      }
    })
  }

  confirmarEdicion(){

  }

  confirmarEliminar(user:any){

  }


}
