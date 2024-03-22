import { CarrosComponent } from './carros/carros.component';
import { PecasComponent } from './pecas/pecas.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // { path: 'home', component: HomeComponent },
  { path: 'carros', component: CarrosComponent },
  { path: 'pecas', component: PecasComponent },
  { path: '', redirectTo: '/carros', pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
