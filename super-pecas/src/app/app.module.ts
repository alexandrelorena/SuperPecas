import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { CarrosComponent } from './carros/carros.component';
import { PecasComponent } from './pecas/pecas.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HeaderComponent } from './header/header.component';
import { TemplateComponent } from './template/template.component';
import { HttpClientModule } from '@angular/common/http';
import { ContentComponent } from './content/content.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'carros', component: CarrosComponent },
  { path: 'pecas', component: PecasComponent },
  { path: '', redirectTo: '/carros', pathMatch: 'full' }
];
@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    CarrosComponent,
    PecasComponent,
    HeaderComponent,
    TemplateComponent,
    ContentComponent,
    HomeComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes), HttpClientModule,
    MatSidenavModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
