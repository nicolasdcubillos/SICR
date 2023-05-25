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

  edit:boolean = false;

  menu: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearMenuForm = this.fb.group({
      nombre: ['',[Validators.required]],
      descripcion: ['',[Validators.required]],
      sedeRestaurante: ['',[Validators.required]],  // Relacion muchos a uno 
      menuMenuProductos: ['',[Validators.required]], // Relacion uno a muchos 
    });
  }

  ngOnInit(): void {
    this.getUsuarios();
  }

  getUsuarios(){
    this.admonService.getMenu().subscribe({
      next:(res:any)=>{
        console.log(res);
        this.menu = res.objeto;
      }
    })
  }

  crear(){
    console.log(this.crearMenuForm.value);
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
