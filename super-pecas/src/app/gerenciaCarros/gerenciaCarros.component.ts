import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { NotificationType, NotificationsService } from 'angular2-notifications';
import { Subject, takeUntil } from 'rxjs';
import { ConfirmDialogComponent } from '../ConfirmDialog/ConfirmDialog.component';
import { ErrorDialogComponent } from '../ErrorDialog/ErrorDialog.component';
import { SuccessDialogComponent } from '../SuccessDialog/SuccessDialog.component';import { Carro } from '../models/Carros';
import { CarrosService } from '../carros/carros.service';

@Component({
  selector: 'app-gerencia-carros',
  templateUrl: './gerenciaCarros.component.html',
  styleUrls: ['./gerenciaCarros.component.css']
})
export class GerenciaCarrosComponent implements OnInit {
  private unsubscribe = new Subject<void>;
  carro: Carro = {} as Carro;
  carroForm!: FormGroup;
  fabricantes: String[] = [];
  isEditing: boolean = false;
  hideAddDialog: any;
  loadCarros: any;
  type: any;
  loading: boolean = false;
  nomeModelo: any;
  createForm: any;
  carrosSubscription: any;

  get f(): any {
    return this.carroForm.controls;
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private carrosService: CarrosService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder,
    private _notifications: NotificationsService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const carroId = params['carroId'];
      const nomeModelo = params['nomeModelo'];
      const fabricante = params['fabricante'];
      const codigoUnico = params['codigoUnico'];

      this.createFormGroup(null);

      if (carroId) {
        this.isEditing = true;
        this.getCarro(carroId);
        this.type = "editar";
      } else {
        this.isEditing = false;
        this.createFormGroup(null);
        this.carro = {} as Carro;
        this.type = "adicionar";
      }
    });

    this.getTodosFabricantes();
  }

  getCarro(carroId: number) {
    this.carrosService.getCarro(carroId)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((result: any) => {
        this.carro = result;
        this.createFormGroup(result);
        this.loading = false;
      });
  }

  createFormGroup(data: any): void {
    this.carroForm = this.formBuilder.group({
      carroId: [data ? data.carroId : "0"],
      nomeModelo: [data ? data.nomeModelo : "", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
      fabricante: [data ? data.fabricante : "", [Validators.required]],
      codigoUnico: [data ? data.codigoUnico : "", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    });
  }

  getTodosFabricantes() {
    this.carrosService.getTodosFabricantes()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe({
        next: (response) => {
          this.fabricantes = response;
        },
        error: (error) => {
          this._notifications.create("Erro", error.error, NotificationType.Error);
        }
      });
  }

  isFormularioValido(): boolean {
    return this.carroForm.valid;
  }

    gravarCarro() {
      if (!this.isFormularioValido()) {
        return;
      }

      const nomeModeloControl = this.carroForm?.get('nomeModelo');
      const fabricanteControl = this.carroForm?.get('fabricante');
      const codigoUnicoControl = this.carroForm?.get('codigoUnico');

      if (!nomeModeloControl || !fabricanteControl || !codigoUnicoControl) {
        return;
      }

      const nomeModelo = nomeModeloControl.value;
      const fabricante = fabricanteControl.value;
      const codigoUnico = codigoUnicoControl.value;

      const novoCarro: Carro = {
        nomeModelo: nomeModelo,
        fabricante: fabricante,
        codigoUnico: codigoUnico,
        carroId: 0
      };


    if (this.type === "adicionar") {
      this.carrosService.createCarro(novoCarro)
        .pipe(takeUntil(this.unsubscribe))
        .subscribe({
          next: () => {
            this._notifications.create("Sucesso", "Carro cadastrado com sucesso", NotificationType.Success);
            this.router.navigate(["/carros"]);
            this.limparCampos();
          },

          error: (error) => {
            this._notifications.create("Erro", error.error, NotificationType.Error);
          }
        });
    } else {
      this.carrosService.updateCarro(novoCarro)
        .pipe(takeUntil(this.unsubscribe))
        .subscribe({
          next: () => {
            this._notifications.create("Sucesso", "Carro atualizado com sucesso", NotificationType.Success);
            this.router.navigate(["/carros"]);
            this.limparCampos();
          },
          error: (error) => {
            this._notifications.create("Erro", error.error, NotificationType.Error);
          }
        });
    }
  }

  limparFormulario() {
    this.carroForm.reset();
  }

  salvarCarro(): void {
    if (!this.isFormularioValido()) {
      return;
    }

    const nomeModeloControl = this.carroForm?.get('nomeModelo');
    const fabricanteControl = this.carroForm?.get('fabricante');
    const codigoUnicoControl = this.carroForm?.get('codigoUnico');

    if (!nomeModeloControl || !fabricanteControl || !codigoUnicoControl) {
      return;
    }
    const novoCarro: Carro = this.carroForm.value;

    const nomeModelo = nomeModeloControl.value;
    const fabricante = fabricanteControl.value;
    const codigoUnico = codigoUnicoControl.value;

  if (this.isEditing) {
    this.carrosService.updateCarro(novoCarro)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe({
        next: () => {
          this._notifications.create("Sucesso", "Carro atualizado com sucesso", NotificationType.Success);
          this.router.navigate(['/carros']);
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Carro atualizado com sucesso!'}
          });
        },
        error: (error: any) => {
          this._notifications.create("Erro", error.error, NotificationType.Error);
          alert('Erro ao editar o carro. Por favor, tente novamente mais tarde.');
        }
      });
    } else {
      this.carrosService.createCarro(novoCarro)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe({
        next: () => {
          this._notifications.create("Sucesso", "Carro cadastrado com sucesso", NotificationType.Success);
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Carro cadastrado com sucesso!' }
          });
          this.limparCampos();
          this.router.navigate(['/carros']);
        },
        error: (error: any) => {
          this._notifications.create("Erro", error.error, NotificationType.Error);
          this.dialog.open(ErrorDialogComponent, {
            width: '400px',
            data: { title: 'Cadastro não efetuado', message: 'O carro informado já existe!' }
          });
        }
      });
    }
  }

  showMessage(message: string) {
    this.dialog.open(ErrorDialogComponent, {
      width: '380px',
      data: { message: message },
      panelClass: 'custom-dialog-panel'
    });
  }

  voltarParaCarros() {
    this.router.navigate(['/carros']);
  }

  limparCampos() {
    this.carroForm.reset();
  }

  ngOnDestroy() {
    if (this.carrosSubscription) {
      this.carrosSubscription.unsubscribe();
    }
  }
}
