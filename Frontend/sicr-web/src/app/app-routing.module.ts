import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SigninComponent } from './components/signin/signin.component';
import { GestionarUsuariosComponent } from './components/gestionar-usuarios/gestionar-usuarios.component';
import { HomeClienteComponent } from './components/home-cliente/home-cliente.component';
import { HomeAdminComponent } from './components/home-admin/home-admin.component';
import { GestionarMenuComponent } from './components/gestionar-menu/gestionar-menu.component';
import { GestionarItemComponent } from './components/gestionar-item/gestionar-item.component';
import { GestionarSedesComponent } from './components/gestionar-sedes/gestionar-sedes.component';
import { GestionarProductoComponent } from './components/gestionar-producto/gestionar-producto.component';
import { DetalleSedeComponent } from './components/detalle-sede/detalle-sede.component';
import { RealizarPedidoComponent } from './components/realizar-pedido/realizar-pedido.component';

const routes: Routes = [
  	{
      path: 'login',
      component: LoginComponent
    },
  	{
      path: 'signin',
      component: SigninComponent
    },
  	{
      path: 'home-admin',
      component: HomeAdminComponent
    },
  	{
      path: 'gestionar-usuarios',
      component: GestionarUsuariosComponent
    },
  	{
      path: 'gestionar-usuarios/:id',
      component: GestionarUsuariosComponent
    },
  	{
      path: 'gestionar-items',
      component: GestionarItemComponent
    },
  	{
      path: 'gestionar-items/:id',
      component: GestionarItemComponent
    },
  	{
      path: 'gestionar-sedes',
      component: GestionarSedesComponent
    },
  	{
      path: 'gestionar-sedes/:id',
      component: GestionarSedesComponent
    },
  	{
      path: 'detalle-sede/:id',
      component: DetalleSedeComponent
    },
  	{
      path: 'gestionar-productos',
      component: GestionarProductoComponent
    },
  	{
      path: 'gestionar-productos/:id',
      component: GestionarProductoComponent
    },
  	{
      path: 'gestionar-menu',
      component: GestionarMenuComponent
    },
  	{
      path: 'gestionar-menu/:id',
      component: GestionarMenuComponent
    },
  	{
      path: 'gestionar-restaurantes',
      component: GestionarUsuariosComponent
    },
  	{
      path: 'realizar-pedido',
      component: RealizarPedidoComponent
    },
  	{
      path: 'home',
      component: HomeClienteComponent
    },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
