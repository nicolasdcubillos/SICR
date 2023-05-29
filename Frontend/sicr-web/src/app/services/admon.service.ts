import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdmonService {
  endpoint:string= environment.API_URI.ms_admon;
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }


  //USUARIOS
  getUsaurios(){
    return this.http.get(`${this.endpoint}/Usuario/getAll`);
  }

  getUsauriosById(userId:string){
    let api = `${this.endpoint}/Usuario/getById`;
    let params = new HttpParams();
    params = params.append('id', userId);
    return this.http.get(api,{params: params});
  }

  updateUsaurios(user:any,userId:string){
    let api = `${this.endpoint}/Usuario/actualizar`;
    let params = new HttpParams();
    params = params.append('id', userId);
    return this.http.post(api,user,{params: params});
  }

  crearUsaurio(user:any){
    let api = `${this.endpoint}/Usuario/crear`
    return this.http.post(api,user);
  }

  eliminarUsuario(userId:string){
    let api = `${this.endpoint}/Usuario/eliminar`;
    let params = new HttpParams();
    params = params.append('id', userId);
    return this.http.get(api,{params: params});
  }

  //ITEMS
  getItems(){
    return this.http.get(`${this.endpoint}/Item/getAll`);
  }

  getItemById(itemId:string){
    let api = `${this.endpoint}/Item/getById`;
    let params = new HttpParams();
    params = params.append('id', itemId);
    return this.http.get(api,{params: params});
  }

  updateItem(item:any,itemId:string){
    let api = `${this.endpoint}/Item/actualizar`;
    let params = new HttpParams();
    params = params.append('id', itemId);
    return this.http.post(api,item,{params: params});
  }

  crearItem(item:any){
    let api = `${this.endpoint}/Item/crear`
    return this.http.post(api,item);
  }

  eliminarItem(itemId:string){
    let api = `${this.endpoint}/Item/eliminar`;
    let params = new HttpParams();
    params = params.append('id', itemId);
    return this.http.get(api,{params: params});
  }

  //ProductoItem

  crearProductoItem(productoItem:any){
    let api = `${this.endpoint}/ProductoItem/crear`
    return this.http.post(api,productoItem);
  }

  eliminarProductoItem(productoItemId:string){
    let api = `${this.endpoint}/ProductoItem/eliminar`;
    let params = new HttpParams();
    params = params.append('id', productoItemId);
    return this.http.get(api,{params: params});
  }

  getItemsByProductId(productId:number,producto:any){
    let api = `${this.endpoint}/ProductoItem/getByProductoId`;
    let params = new HttpParams();
    params = params.append('id', productId);
    return this.http.post(api,producto,{params: params});
  }

   //SEDES
   getSedes(){
    return this.http.get(`${this.endpoint}/SedeRestaurante/getAll`);
  }

  getSedeById(sedeId:string){
    let api = `${this.endpoint}/SedeRestaurante/getById`;
    let params = new HttpParams();
    params = params.append('id', sedeId);
    return this.http.get(api,{params: params});
  }

  updateSede(sede:any,sedeId:string){
    let api = `${this.endpoint}/SedeRestaurante/actualizar`;
    let params = new HttpParams();
    params = params.append('id', sedeId);
    return this.http.post(api,sede,{params: params});
  }

  crearSede(sede:any){
    let api = `${this.endpoint}/SedeRestaurante/crear`
    return this.http.post(api,sede);
  }

  eliminarSede(sedeId:string){
    let api = `${this.endpoint}/SedeRestaurante/eliminar`;
    let params = new HttpParams();
    params = params.append('id', sedeId);
    return this.http.get(api,{params: params});
  }

  //Prodcuto
   getProductos(){
    return this.http.get(`${this.endpoint}/Producto/getAll`);
  }

  getProductoById(productoId:string){
    let api = `${this.endpoint}/Producto/getById`;
    let params = new HttpParams();
    params = params.append('id', productoId);
    return this.http.get(api,{params: params});
  }

  updateProducto(producto:any,productoId:string){
    let api = `${this.endpoint}/Producto/actualizar`;
    let params = new HttpParams();
    params = params.append('id', productoId);
    return this.http.post(api,producto,{params: params});
  }

  crearProducto(producto:any){
    let api = `${this.endpoint}/Producto/crear`
    return this.http.post(api,producto);
  }

  eliminarProducto(productoId:string){
    let api = `${this.endpoint}/Producto/eliminar`;
    let params = new HttpParams();
    params = params.append('id', productoId);
    return this.http.get(api,{params: params});
  }

  //EstadoProducto
  getEstadosProducto(){
    return this.http.get(`${this.endpoint}/EstadoProducto/getAll`);
  }

  //EstadoProducto
  getCategorias(){
    return this.http.get(`${this.endpoint}/Categoria/getAll`);
  }

  //Restaurante
  getRestaurantes(){
    return this.http.get(`${this.endpoint}/Restaurante/getAll`);
  }

  //Tipo Usuarios
  getTipoUsuario(){
    return this.http.get(`${this.endpoint}/TipoUsuario/getAll`);
  }

  //Tipo Miembros
  getTipoMiembros(){
    return this.http.get(`${this.endpoint}/TipoMiembro/getAll`);
  }

  //Miembros
  getMiembroBySedeId(sedeId: string){
    let api = `${this.endpoint}/Miembro/getByIdSedeRestaurante`;
    let params = new HttpParams();
    params = params.append('id', sedeId);
    return this.http.get(api,{params: params});
  }
  getMiembroById(miembroId:string){
    let api = `${this.endpoint}/Miembro/getById`;
    let params = new HttpParams();
    params = params.append('id', miembroId);
    return this.http.get(api,{params: params});
  }

  updateMiembro(miembro:any,miembroId:string){
    let api = `${this.endpoint}/Miembro/actualizar`;
    let params = new HttpParams();
    params = params.append('id', miembroId);
    return this.http.post(api,miembro,{params: params});
  }

  crearMiembro(miembro:any){
    let api = `${this.endpoint}/Miembro/crear`
    return this.http.post(api,miembro);
  }

  eliminarMiembro(miembroId:string){
    let api = `${this.endpoint}/Miembro/eliminar`;
    let params = new HttpParams();
    params = params.append('id', miembroId);
    return this.http.get(api,{params: params});
  }


  //ItemSede
  getItemsBySedeId(sedeId: string){
    let api = `${this.endpoint}/ItemSedeRestaurante/getBySedeId`;
    let params = new HttpParams();
    params = params.append('id', sedeId);
    return this.http.get(api,{params: params});
  }
  getItemSedeById(ItemSedeId:string){
    let api = `${this.endpoint}/ItemSedeRestaurante/getById`;
    let params = new HttpParams();
    params = params.append('id', ItemSedeId);
    return this.http.get(api,{params: params});
  }

  updateItemSede(itemSede:any,ItemSedeId:string){
    let api = `${this.endpoint}/ItemSedeRestaurante/actualizar`;
    let params = new HttpParams();
    params = params.append('id', ItemSedeId);
    return this.http.post(api,itemSede,{params: params});
  }

  crearItemSede(itemSede:any){
    let api = `${this.endpoint}/ItemSedeRestaurante/crear`
    return this.http.post(api,itemSede);
  }

  eliminarItemSede(ItemSedeId:string){
    let api = `${this.endpoint}/ItemSedeRestaurante/eliminar`;
    let params = new HttpParams();
    params = params.append('id', ItemSedeId);
    return this.http.get(api,{params: params});
  }


  //Menu
  getMenus(){
    return this.http.get(`${this.endpoint}/Menu/getAll`);
  }

  getMenuById(menuId:string){
    let api = `${this.endpoint}/Menu/getById`;
    let params = new HttpParams();
    params = params.append('id', menuId);
    return this.http.get(api,{params: params});
  }

  updateMenu(menu:any){
    let api = `${this.endpoint}/Menu/actualizar`;
    return this.http.post(api,menu);
  }

  crearMenu(menu:any){
    let api = `${this.endpoint}/Menu/crear`
    return this.http.post(api,menu);
  }

  eliminarMenu(menuId:string){
    let api = `${this.endpoint}/Menu/eliminar`;
    let params = new HttpParams();
    params = params.append('id', menuId);
    return this.http.get(api,{params: params});
  }

  //MenuProducto

  crearMenuProducto(productoItem:any){
    let api = `${this.endpoint}/MenuProducto/crear`
    return this.http.post(api,productoItem);
  }

  eliminarMenuProducto(productoItemId:string){
    let api = `${this.endpoint}/MenuProducto/eliminar`;
    let params = new HttpParams();
    params = params.append('id', productoItemId);
    return this.http.get(api,{params: params});
  }

  getProductosByMenuId(menuId:number,menu:any){
    let api = `${this.endpoint}/MenuProducto/getByMenuId`;
    let params = new HttpParams();
    params = params.append('id', menuId);
    return this.http.post(api,menu,{params: params});
  }

}
