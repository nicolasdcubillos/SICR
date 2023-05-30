import { Component } from '@angular/core';
import { RestauranteService } from './service/restaurante.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SeguridadService } from './service/seguridad.service';
import { ToastrService } from 'ngx-toastr';
import { UsuarioService } from './service/usuario.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  sedes: any;
  restaurante: any;

  showModal = false;
  showModal2 = false;

  loginForm: FormGroup;
  registroForm: FormGroup;

  constructor(public fb: FormBuilder,
    private seguridadServide:SeguridadService ,
    private restauranteService: RestauranteService,
    private usaurioService:UsuarioService,
    private toastr: ToastrService,) { 
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
    this.registroForm = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required],
      nombres: ['',Validators.required],
      apellidos: ['',Validators.required],
      email: ['',Validators.required],
      telefono: ['',Validators.required],
      direccion: ['',Validators.required],
      latitud: [0],
      longitud: [0],
      tipoUsuario: [4],
    });
  }

  ngOnInit() {
    // Traer restaurante principal - id 1.

    this.restauranteService.getById(1).subscribe((response: any) => {
      console.log(response);
      if (response.exitosa) {
        this.restaurante = response.objeto;
      } else {
        //this.toastr.error('No se pudo traer la información de los menús. Intente más tarde.', 'Error')
      }
    });
  }

  iniciarSesion(){
    console.log(this.loginForm.value);
    this.seguridadServide.login(this.loginForm.value);
    this.closeModal();
    this.toastr.success("Login Exitoso", 'Success')
  }

  registrarse(){
    this.closeModal();
    this.openModal2();
  }

  confirmarRegistro(){
    this.usaurioService.crearUsaurio(this.registroForm.value).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          this.closeModal2();
          this.toastr.success("Registro Exitoso", 'Success');
          this.openModal();
        }
      }
    })
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }
  openModal2() {
    this.showModal2 = true;
  }

  closeModal2() {
    this.showModal2 = false;
  }
}