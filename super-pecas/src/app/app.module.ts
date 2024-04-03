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
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { TableModule } from 'primeng/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { PaginatorModule } from 'primeng/paginator';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
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
import { GerenciaCarrosComponent } from './gerenciaCarros/gerenciaCarros.component';
import { ConfirmDialogComponent } from './ConfirmDialog/ConfirmDialog.component';
import { GerenciaPecasComponent } from './gerenciaPecas/gerenciaPecas.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'carros', component: CarrosComponent },
  { path: 'pecas', component: PecasComponent },
  { path: 'gerenciar', component: GerenciaCarrosComponent },
  { path: 'gerenciar-pecas', component: GerenciaPecasComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
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
    HomeComponent,
    GerenciaCarrosComponent,
    ConfirmDialogComponent,
    GerenciaPecasComponent
   ],

  imports: [

    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    AppRoutingModule,
    MatSidenavModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatPaginatorModule,
    MatDialogModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    PaginatorModule,
    DialogModule,
    CanvasJSAngularChartsModule,
    InputGroupModule,
    InputGroupAddonModule
  ],

  providers: [
    ApiService,
    provideAnimationsAsync('noop')
  ],
  bootstrap: [AppComponent]

})
export class AppModule { }

