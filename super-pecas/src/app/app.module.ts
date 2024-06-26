import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { SimpleNotificationsModule } from 'angular2-notifications';
import { MatOptionModule } from '@angular/material/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';
import { TableModule } from 'primeng/table';
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
import { FooterComponent } from './footer/footer.component';
import { MenuComponent } from './menu/menu.component';
import { CarrosComponent } from './carros/carros.component';
import { PecasComponent } from './pecas/pecas.component';
import { HeaderComponent } from './header/header.component';
import { TemplateComponent } from './template/template.component';
import { ContentComponent } from './content/content.component';
import { HomeComponent } from './home/home.component';
import { ApiService } from '../../services/api.service';
import { GerenciaCarrosComponent } from './gerenciaCarros/gerenciaCarros.component';
import { ConfirmDialogComponent } from './ConfirmDialog/ConfirmDialog.component';
import { GerenciaPecasComponent } from './gerenciaPecas/gerenciaPecas.component';
import { ConfirmDialogPecaComponent } from './ConfirmDialogPeca/ConfirmDialogPeca.component';
import { ErrorDialogComponent } from './ErrorDialog/ErrorDialog.component';
import { SuccessDialogComponent } from './SuccessDialog/SuccessDialog.component';


@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    MenuComponent,
    CarrosComponent,
    PecasComponent,
    HeaderComponent,
    TemplateComponent,
    ContentComponent,
    HomeComponent,
    GerenciaCarrosComponent,
    ConfirmDialogComponent,
    GerenciaPecasComponent,
    ConfirmDialogPecaComponent,
    ErrorDialogComponent,
    SuccessDialogComponent
   ],

  imports: [

    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule,
    AppRoutingModule,
    MatSidenavModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatPaginatorModule,
    MatDialogModule,
    MatOptionModule,
    MatFormFieldModule,
    MatSelectModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    PaginatorModule,
    DialogModule,
    CanvasJSAngularChartsModule,
    InputGroupModule,
    InputGroupAddonModule,
    SimpleNotificationsModule.forRoot(),
    FormsModule,
    ReactiveFormsModule
  ],

  providers: [
    ApiService,
    provideAnimationsAsync('noop')
  ],
  bootstrap: [AppComponent]

})
export class AppModule { }

