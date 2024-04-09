import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Subject, takeUntil } from 'rxjs';
import { Peca } from '../models/Pecas';
// import { Pecas } from '../pecas/peca.interface';
import { PecasService } from '../pecas/pecas.service';
import { CarrosService } from '../carros/carros.service';
// import { Carros } from '../carros/carro.interface';
import { Carro } from '../models/Carros';
import { ConfirmDialogComponent } from '../ConfirmDialog/ConfirmDialog.component';
import { ErrorDialogComponent } from '../ErrorDialog/ErrorDialog.component';
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
  showMessage: any;
  loadPecas: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pecasService: PecasService,
    private carrosService: CarrosService,
    private dialog: MatDialog

  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const pecaId = params['pecaId'];
      if (pecaId) {
        // Editar
        this.isEditing = true;
        this.pecasService.getPeca(pecaId).subscribe((peca: Peca) => {
          this.peca = peca;
        });
      } else {
        // Cadastrar
        this.isEditing = false;
        this.peca = {} as Peca;
      }
    });
  }

  salvarPeca(): void {
    if (!this.peca.nome || !this.peca.descricao || !this.peca.numeroSerie || !this.peca.fabricante || !this.peca.modeloCarro) {
      this.showMessage('Todos os campos devem ser preenchidos.');
      return;
    }

    if (this.isEditing) {

      this.pecasService.updatePeca({
        pecaId: this.peca.pecaId,
        nome: this.peca.nome,
        descricao: this.peca.descricao,
        numeroSerie: this.peca.numeroSerie,
        fabricante: this.peca.fabricante,
        modeloCarro: this.peca.modeloCarro,
        carroId: this.peca.carroId
      }).subscribe({
        next: () => {
          this.router.navigate(['/pecas']);
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Peça atualizado com sucesso!'}
          });
        },
        error: (error: any) => {
          // console.error('Erro ao editar o peça:', error);
          alert('Erro ao editar a peça. Por favor, tente novamente mais tarde.');
        }
      });

    } else {

      this.pecasService.createPeca(this.peca).subscribe({
        next: () => {
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Peça cadastrado com sucesso!' }
          });
          this.limparCampos();
          this.loadPecas();
        },
        error: (error: any) => {
          console.error('Erro ao cadastrar a peça:', error);
          alert('Ocorreu um erro ao cadastrar a peça. Por favor, tente novamente mais tarde.');
        }
      });
    }
  }

  voltarParaPecas() {
    this.router.navigate(['/pecas']);
  }

  limparCampos() {
    this.peca = {} as Peca;
  }

}
