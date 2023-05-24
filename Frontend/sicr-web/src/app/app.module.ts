import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SigninComponent } from './components/signin/signin.component';
import { HomeAdminComponent } from './components/home-admin/home-admin.component';
import { GestionarMiembrosComponent } from './components/gestionar-miembros/gestionar-miembros.component';
import { GestionarInventarioComponent } from './components/gestionar-inventario/gestionar-inventario.component';
import { GestionarReservasComponent } from './components/gestionar-reservas/gestionar-reservas.component';
import { GestionarPromocionesComponent } from './components/gestionar-promociones/gestionar-promociones.component';
import { GestionarUsuariosComponent } from './components/gestionar-usuarios/gestionar-usuarios.component';
import { GestionarMenuComponent } from './components/gestionar-menu/gestionar-menu.component';
import { GestionarCategoriaComponent } from './components/gestionar-categoria/gestionar-categoria.component';
import { GestionarRestaurantesComponent } from './components/gestionar-restaurantes/gestionar-restaurantes.component';
import { GestionarSedesComponent } from './components/gestionar-sedes/gestionar-sedes.component';
import { HomeClienteComponent } from './components/home-cliente/home-cliente.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SigninComponent,
    HomeAdminComponent,
    GestionarMiembrosComponent,
    GestionarInventarioComponent,
    GestionarReservasComponent,
    GestionarPromocionesComponent,
    GestionarUsuariosComponent,
    GestionarMenuComponent,
    GestionarCategoriaComponent,
    GestionarRestaurantesComponent,
    GestionarSedesComponent,
    HomeClienteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
