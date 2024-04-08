import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Subject, takeUntil } from 'rxjs';
import { Peca } from '../models/Pecas';
// import { Pecas } from '../pecas/peca.interface';
import { PecasService } from '../pecas/pecas.service';
import { CarrosService } from '../carros/carros.service';
// import { Carros } from '../carros/carro.interface';
import { Carro } from '../models/Carros';
// import { NotificationType } from 'angular2-notifications';

@Component({
  selector: 'app-gerencia-Pecas',
  templateUrl: './gerenciaPecas.component.html',
  styleUrls: ['./gerenciaPecas.component.css']
})

export class GerenciaPecasComponent implements OnInit {
  peca: Peca = {} as Peca;
  isEditing: boolean = false;
  carros: Carro[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pecasService: PecasService,
    private carrosService: CarrosService

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
    // Verificar se todos os campos estão preenchidos
    if (!this.peca.nome || !this.peca.descricao || !this.peca.numeroSerie || !this.peca.fabricante || !this.peca.modeloCarro) {
      alert('Todos os campos devem ser preenchidos.');
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
          alert('Peça editada com sucesso!');
        },
        error: (error: any) => {
          console.error('Erro ao editar o peça:', error);
          alert('Erro ao editar a peça. Por favor, tente novamente mais tarde.');
        }
      });

    } else {

      this.pecasService.createPeca(this.peca).subscribe({
        next: () => {
          this.router.navigate(['/cadastrar-peca']);
          alert('Peça cadastrado com sucesso!');
          setTimeout(() => {
            location.reload();
          }, 1000);
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
