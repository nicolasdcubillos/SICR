import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AdmonService } from 'src/app/services/admon.service';
import Swal from 'sweetalert2';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-gestionar-producto',
  templateUrl: './gestionar-producto.component.html',
  styleUrls: ['./gestionar-producto.component.css']
})
export class GestionarProductoComponent implements OnInit {

  crearProductoForm:FormGroup;

  edit:boolean = false;

  productoEdit:any ;
  prodcutoEditSend:any={} ;

  productos: any=[];
  items: any=[];
  categorias: any=[];
  estadosProducto: any=[];

  constructor(public fb: FormBuilder,private router:Router,private activatedRoute:ActivatedRoute, private admonService:AdmonService){
    this.crearProductoForm = this.fb.group({
      nombre: ['',[Validators.required]],
      categoria: ['',[Validators.required]],
      estadoProducto: ['',[Validators.required]],
    });
  }

  ngOnInit(): void {
    const params =  this.activatedRoute.snapshot.params;
    this.getProductos();
    this.getCategoria();
    this.getEstadoProducto();
    this.getItems();
    if(params['id']) {
      this.edit=true;
      this.admonService.getProductoById(params['id']).subscribe({
        next:(res:any)=>{
          this.productoEdit=res.objeto;
          console.log(this.productoEdit)
          this.crearProductoForm.get('nombre')!.setValue(this.productoEdit.nombre);
          this.crearProductoForm.get('categoria')!.setValue(this.productoEdit.categoria);
          this.crearProductoForm.get('estadoProducto')!.setValue(this.productoEdit.estadoProducto);
        }
      })
    }
  }

  //Chips
  //Chips PropertyType

  public chipSelectedProperty: any[] = [];
  public filteredProperty!: Observable<String[]>;
  private allowFreeTextAddProperty = false;
  public propertyControl = new FormControl();
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  @ViewChild('propertyInput') propertyInput!: ElementRef<HTMLInputElement>;
  @ViewChild('auto3') matAutocomplete3!: MatAutocomplete;

  public addProperty(event: MatChipInputEvent): void {
    if (!this.allowFreeTextAddProperty) {
      console.log('allowFreeTextAddProperty is false');
      return;
    }
    if (this.matAutocomplete3.isOpen) {
      return;
    }

     const value = event.value;
     if ((value || '').trim()) {
      this.selectPropertyByName(value.trim());
    }

    this.resetInputs3();
  }

  public removeProperty(property: any): void {
    const index = this.chipSelectedProperty.indexOf(property);
    if (index >= 0) {
      this.chipSelectedProperty.splice(index, 1);
      this.resetInputs3();
    }
  }

  public propertySelected(event: MatAutocompleteSelectedEvent): void {
    this.selectPropertyByName(event.option.value);
    this.resetInputs3();
  }

  private resetInputs3() {
    this.propertyInput.nativeElement.value = '';
    this.propertyControl.setValue(null); 
  }
  private filterOnValueChange3(propertyName: string | null): String[] {
    let result: String[] = [];
    let allPropertyLessSelected = this.items.filter((property: any) => this.chipSelectedProperty.indexOf(property) < 0);
    if (propertyName) {
      result = this.filterProperty(allPropertyLessSelected, propertyName);
    } else {
      result = allPropertyLessSelected.map((property: { name: any; }) => property.name);
    }
    return result;
  }

  private filterProperty(propertyList: any[], propertyName: String): String[] {
    let filteredPropertyList: any[] = [];
    const filterValue = propertyName.toLowerCase();
    let propertiesMatchingPropertyName = propertyList.filter(property => property.name.toLowerCase().indexOf(filterValue) === 0);
    if (propertiesMatchingPropertyName.length || this.allowFreeTextAddProperty) {

      filteredPropertyList = propertiesMatchingPropertyName;
    } else {

      filteredPropertyList = propertyList;
    }

    return filteredPropertyList.map(city => city.name);
  }

  private selectPropertyByName(propertyName: string) {
    let foundProperty = this.items.filter((property: { name: string; }) => property.name == propertyName);
    if (foundProperty.length) {

      this.chipSelectedProperty.push(foundProperty[0]);
    } else {

      let highestEmployeeId = Math.max(...this.chipSelectedProperty.map(property => property.id), 0);
      this.chipSelectedProperty.push({ name: propertyName, id: highestEmployeeId  });
    }
  }


  getItems(){
    this.admonService.getItems().subscribe({
      next:(res:any)=>{
        this.items = res.objeto;
      }
    })
  }

  getCategoria(){
    this.admonService.getCategorias().subscribe({
      next:(res:any)=>{
        this.categorias = res.objeto;
      }
    })
  }

  getEstadoProducto(){
    this.admonService.getEstadosProducto().subscribe({
      next:(res:any)=>{
        this.estadosProducto = res.objeto;
      }
    })
  }

  getProductos(){
    this.admonService.getProductos().subscribe({
      next:(res:any)=>{
        this.productos = res.objeto;
        console.log(this.productos);
      }
    })
  }

  crear(){
    this.admonService.crearUsaurio(this.crearProductoForm.value).subscribe({
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
    this.productoEdit.nombres = this.crearProductoForm.value.nombres
    this.productoEdit.apellidos = this.crearProductoForm.value.apellidos
    this.productoEdit.username = this.crearProductoForm.value.username
    this.productoEdit.direccion = this.crearProductoForm.value.direccion
    this.productoEdit.telefono = this.crearProductoForm.value.telefono
    this.productoEdit.email = this.crearProductoForm.value.email
    this.productoEdit.tipoUsuario = this.crearProductoForm.value.tipoUsuario
    this.productoEdit.password = this.crearProductoForm.value.password

    console.log(this.productoEdit);

    this.admonService.updateUsaurios(this.productoEdit,this.productoEdit.id).subscribe({
      next:(res:any)=>{
        if(res.exitosa){
          Swal.fire({
            title: 'Usuario Editado',
            icon: 'success',
            showCloseButton:true,
            confirmButtonText:"Aceptar",
            confirmButtonColor: "#DD6B55",
          }).then(()=>{
            this.router.navigate(['/gestionar-productos'])
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
            this.router.navigate(['/gestionar-productos'])
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
