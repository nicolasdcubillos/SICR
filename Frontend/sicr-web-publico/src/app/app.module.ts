import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InicioComponent } from './inicio/inicio.component';
import { HttpClientModule } from '@angular/common/http';
import { SedeRestauranteComponent } from './sede-restaurante/sede-restaurante.component';
import { ReservaComponent } from './reserva/reserva.component';
import {FormsModule} from "@angular/forms";
import { ToastrModule } from 'ngx-toastr';
import { NgxSpinnerModule } from 'ngx-spinner';

const routes: Routes = [
  { path: '', component: InicioComponent },
  { path: 'sede', component: SedeRestauranteComponent },
  { path: 'reservar', component: ReservaComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    InicioComponent,
    SedeRestauranteComponent,
    ReservaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ToastrModule.forRoot(),
    NgxSpinnerModule.forRoot({ type: 'ball-scale-multiple' })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
