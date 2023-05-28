import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { SedeRestauranteComponent } from './sede-restaurante/sede-restaurante.component';
import { ReservaComponent } from './reserva/reserva.component';

const routes: Routes = [
  { path: '', component: InicioComponent },
  { path: 'sede/:id', component: SedeRestauranteComponent },
  { path: 'reservar/:idSede', component: ReservaComponent },
  { path: '**', redirectTo: '' } // Asegúrate de que esta ruta esté al final
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
