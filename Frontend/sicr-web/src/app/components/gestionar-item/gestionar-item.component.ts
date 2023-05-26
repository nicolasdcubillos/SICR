import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gestionar-item',
  templateUrl: './gestionar-item.component.html',
  styleUrls: ['./gestionar-item.component.css']
})
export class GestionarItemComponent implements OnInit {

  crearItemForm:FormGroup;

  edit:boolean = false;

  itemEdit:any ;
  itemEditSend:any={} ;

  items: any=[];
  tipoUsuarios: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearItemForm = this.fb.group({
      nombre: ['',[Validators.required]],
      costoUnitario: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getItems();
    if(params['id']) {
      this.edit=true;
      this.admonService.getItemById(params['id']).subscribe({
        next:(res:any)=>{
          this.itemEdit=res.objeto;
          console.log(this.itemEdit)
          this.crearItemForm.get('nombre')!.setValue(this.itemEdit.nombre);
          this.crearItemForm.get('costoUnitario')!.setValue(this.itemEdit.costoUnitario);
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

  crear(){
    this.admonService.crearItem(this.crearItemForm.value).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res.exitosa){
          Swal.fire({
            title: 'Item Creado',
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
      title: 'Esta seguro de editar este item ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.editarItem();
    })
  }

  editarItem(){
    this.itemEdit.nombre = this.crearItemForm.value.nombre
    this.itemEdit.costoUnitario = this.crearItemForm.value.costoUnitario

    console.log(this.itemEdit);

    this.admonService.updateItem(this.itemEdit,this.itemEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Item Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
          this.router.navigate(['/gestionar-items'])
        }else{
          Swal.fire({
            title: 'Error',
            icon: 'error',
            text: res.descripcionExcepcion,
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
          this.router.navigate(['/gestionar-items'])
        }
      }
    })
  }

  confirmarEliminar(item:any){
    Swal.fire({
      title: 'Esta seguro de eliminar este item ?',
      icon: 'warning',
      showCloseButton:true,
      confirmButtonText:"Aceptar",
      confirmButtonColor: "#DD6B55",
    }).then((result)=>{
      if(result.isConfirmed)this.eliminarItem(item);
    })
  }

  eliminarItem(item:any){
    this.admonService.eliminarItem(item.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Eliminado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          })
          window.location.reload();
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
