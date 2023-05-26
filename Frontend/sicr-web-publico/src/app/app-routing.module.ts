import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { InicioComponent } from './inicio/inicio.component';
import { SedeRestauranteComponent } from './sede-restaurante/sede-restaurante.component';

const routes: Routes = [
  { path: '', component: InicioComponent },
  { path: 'sede/:id', component: SedeRestauranteComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
