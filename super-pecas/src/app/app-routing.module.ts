import { HomeComponent } from './home/home.component';
import { CarrosComponent } from './carros/carros.component';
import { PecasComponent } from './pecas/pecas.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GerenciaCarrosComponent } from './gerenciaCarros/gerenciaCarros.component';
import { GerenciaPecasComponent } from './gerenciaPecas/gerenciaPecas.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'carros', component: CarrosComponent },
  { path: 'pecas', component: PecasComponent },
  { path: 'gerenciar/:carroId', component: GerenciaCarrosComponent },
  { path: 'gerenciar-pecas/:pecaId', component: GerenciaPecasComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
