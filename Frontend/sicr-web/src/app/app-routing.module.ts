import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SigninComponent } from './components/signin/signin.component';
import { GestionarUsuariosComponent } from './components/gestionar-usuarios/gestionar-usuarios.component';
import { HomeClienteComponent } from './components/home-cliente/home-cliente.component';
import { HomeAdminComponent } from './components/home-admin/home-admin.component';
import { GestionarMenuComponent } from './components/gestionar-menu/gestionar-menu.component';

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
      path: 'gestionar-menu',
      component: GestionarMenuComponent
    },
  	{
      path: 'gestionar-restaurantes',
      component: GestionarUsuariosComponent
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
