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
  { path: 'gerenciar-carros/:carroId', component: GerenciaCarrosComponent },
  { path: 'gerenciar-pecas/:pecaId', component: GerenciaPecasComponent },
  { path: 'gerenciar-carros', component: GerenciaCarrosComponent },
  { path: 'gerenciar-pecas', component: GerenciaPecasComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
