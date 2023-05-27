import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

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
import { AuthInterceptor } from './services/authconfig.interceptor';
import { GestionarItemComponent } from './components/gestionar-item/gestionar-item.component';
import { GestionarProductoComponent } from './components/gestionar-producto/gestionar-producto.component';

//Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

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
    HomeClienteComponent,
    GestionarItemComponent,
    GestionarProductoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatIconModule,
    MatInputModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
