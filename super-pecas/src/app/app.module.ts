import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PaginatorModule } from 'primeng/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogModule } from 'primeng/dialog';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { CarrosComponent } from './carros/carros.component';
import { PecasComponent } from './pecas/pecas.component';
import { HeaderComponent } from './header/header.component';
import { TemplateComponent } from './template/template.component';
import { ContentComponent } from './content/content.component';
import { HomeComponent } from './home/home.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';
import { ApiService } from '../../services/api.service';

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
    RouterModule.forRoot(routes),
    HttpClientModule,
    MatSidenavModule,
    MatButtonModule,
    MatInputModule,
    CanvasJSAngularChartsModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    TableModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    MatPaginatorModule,
    PaginatorModule,
    MatDialogModule,
    DialogModule
  ],
  providers: [
    ApiService,
    provideAnimationsAsync('noop')
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

