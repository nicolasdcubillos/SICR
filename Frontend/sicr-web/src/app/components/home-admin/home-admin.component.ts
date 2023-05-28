import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit{

  sedes: any=[];

  sedeAdmin:FormGroup;

  constructor(public fb: FormBuilder,private router:Router, private admonService:AdmonService){
    this.sedeAdmin = this.fb.group({
      sedeId: [''],
    })

  }

  ngOnInit(): void {
    this.getSedes();
  }

  getSedes(){
    this.admonService.getSedes().subscribe({
      next:(res:any)=>{
        this.sedes = res.objeto;
      }
    })
  }

  redirectAdminSede(){
    console.log(this.sedeAdmin.value)
    if(this.sedeAdmin.value.sedeId == ''){
      Swal.fire({
        title: 'No has selesccionado una sede',
        icon: 'info',
        text:'Por favor selecciona una sede para administrar',
        showCloseButton:true,
        confirmButtonText:"Aceptar",
        confirmButtonColor: "#DD6B55",
      })
    }else{
      this.router.navigate(['/detalle-sede', this.sedeAdmin.value.sedeId]);
    }
  }

}
