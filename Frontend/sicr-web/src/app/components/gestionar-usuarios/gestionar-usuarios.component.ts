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

  usuarioEdit:any ;
  usuarioEditSend:any={} ;

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
    const params =  this.activatedRoute.snapshot.params;
    this.getUsuarios();
    this.getTipoUsaurios();
    if(params['id']) {
      this.edit=true;
      this.admonService.getUsauriosById(params['id']).subscribe({
        next:(res:any)=>{
          this.usuarioEdit=res.objeto;
          console.log(this.usuarioEdit)
          this.crearUsuarioForm.get('nombres')!.setValue(this.usuarioEdit.nombres);
          this.crearUsuarioForm.get('apellidos')!.setValue(this.usuarioEdit.apellidos);
          this.crearUsuarioForm.get('username')!.setValue(this.usuarioEdit.username);
          this.crearUsuarioForm.get('telefono')!.setValue(this.usuarioEdit.telefono);
          this.crearUsuarioForm.get('email')!.setValue(this.usuarioEdit.email);
          this.crearUsuarioForm.get('direccion')!.setValue(this.usuarioEdit.direccion);
          this.crearUsuarioForm.get('tipoUsuario')!.setValue(this.usuarioEdit.tipoUsuario);
          this.crearUsuarioForm.get('password')!.setValue(this.usuarioEdit.password);
        }
      })
    }
  }

  getTipoUsaurios(){
    this.admonService.getTipoUsuario().subscribe({
      next:(res:any)=>{
        this.tipoUsuarios = res.objeto;
      }
    })
  }

  getUsuarios(){
    this.admonService.getUsaurios().subscribe({
      next:(res:any)=>{
        this.usuarios = res.objeto;
      }
    })
  }

  crear(){
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
    this.usuarioEdit.nombres = this.crearUsuarioForm.value.nombres
    this.usuarioEdit.apellidos = this.crearUsuarioForm.value.apellidos
    this.usuarioEdit.username = this.crearUsuarioForm.value.username
    this.usuarioEdit.direccion = this.crearUsuarioForm.value.direccion
    this.usuarioEdit.telefono = this.crearUsuarioForm.value.telefono
    this.usuarioEdit.email = this.crearUsuarioForm.value.email
    this.usuarioEdit.tipoUsuario = this.crearUsuarioForm.value.tipoUsuario
    this.usuarioEdit.password = this.crearUsuarioForm.value.password

    console.log(this.usuarioEdit);

    this.admonService.updateUsaurios(this.usuarioEdit,this.usuarioEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-usuarios'])
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
            this.router.navigate(['/gestionar-usuarios'])
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
