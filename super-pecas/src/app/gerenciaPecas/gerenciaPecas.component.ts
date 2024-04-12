import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Peca } from '../models/Pecas';
import { PecasService } from '../pecas/pecas.service';
import { CarrosService } from '../carros/carros.service';
import { Carro } from '../models/Carros';
import { SuccessDialogComponent } from '../SuccessDialog/SuccessDialog.component';

@Component({
  selector: 'app-gerencia-Pecas',
  templateUrl: './gerenciaPecas.component.html',
  styleUrls: ['./gerenciaPecas.component.css']
})
export class GerenciaPecasComponent implements OnInit {
  peca: Peca = {} as Peca;
  isEditing: boolean = false;
  carros: Carro[] = [];
  type!: any;
  loading: boolean = false;
  pecaForm!: FormGroup;

  get f(): any {
    return this.pecaForm.controls;
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pecasService: PecasService,
    private carrosService: CarrosService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getCarros();

    this.route.params.subscribe(params => {
      const pecaId = params['pecaId'];
      if (pecaId) {
        // Editar
        this.isEditing = true;
        this.type = "editar";
        this.getPeca(pecaId);
      } else {
        // Cadastrar
        this.isEditing = false;
        this.type = "adicionar";
      }
    });
  }

  createForm(): void {
    this.pecaForm = this.formBuilder.group({
      pecaId: ['0'],
      nome: ['', Validators.required],
      descricao: ['', Validators.required],
      numeroSerie: ['', Validators.required],
      fabricante: ['', Validators.required],
      modeloCarro: ['', Validators.required],
      carroId: ['', Validators.required]
    });
  }

  getPeca(pecaId: number): void {
    this.pecasService.getPeca(pecaId).subscribe((peca: Peca) => {
      this.peca = peca;
      this.pecaForm.patchValue(peca);
    });
  }

  getCarros(): void {
    this.carrosService.getTodosCarros().subscribe(carros => {
      this.carros = carros;
    });
  }

  salvarPeca(): void {
    if (this.pecaForm.invalid) {
      return;
    }

    const formValue = this.pecaForm.value;

    if (this.isEditing) {
      const peca = { ...this.peca, ...formValue };
      this.pecasService.updatePeca(peca).subscribe({
        next: () => {
          this.router.navigate(['/pecas']);
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Peça atualizada com sucesso!' }
          });
          this.limparCampos();
        },
        error: (error: any) => {
          alert('Erro ao editar a peça. Por favor, tente novamente mais tarde.');
        }
      });
    }
     else {
      this.pecasService.createPeca(formValue).subscribe({
        next: () => {
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Peça cadastrada com sucesso!' }
          });
          this.limparCampos();
          this.router.navigate(["/pecas"]);
        },
        error: (error: any) => {
          console.error('Erro ao cadastrar a peça:', error);
          alert('Ocorreu um erro ao cadastrar a peça. Por favor, tente novamente mais tarde.');
        }
      });
    }
  }

  voltarParaPecas(): void {
    this.router.navigate(['/pecas']);
  }

  limparCampos(): void {
    this.pecaForm.reset();
  }
}
