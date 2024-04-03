import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Pecas} from '../models/Pecas';
import { Carros } from '../models/Carros';
import { PecasService } from '../pecas/pecas.service';
import { CarrosService } from '../carros/carros.service';


@Component({
  selector: 'app-gerenciaPecas',
  templateUrl: './gerenciaPecas.component.html',
  styleUrls: ['./gerenciaPecas.component.css']
})
export class GerenciaPecasComponent implements OnInit {
  peca: Pecas = new Pecas();
  isEditing: boolean = false;
  isEditMode: boolean = false;
  // carroId: 0;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pecasService: PecasService

  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const pecaId = params['pecaId'];
      if (pecaId) {
        // Editar
        this.isEditing = true;
        this.pecasService.getPeca(pecaId).subscribe((peca: Pecas) => {
          this.peca = peca;
        });
      } else {
        // Cadastrar
        this.isEditing = false;
        this.peca = new Pecas();
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
      // Salvar carro editado
      this.pecasService.updatePeca({
        pecaId: this.peca.pecaId,
        nome: this.peca.nome,
        descricao: this.peca.descricao,
        numeroSerie: this.peca.numeroSerie,
        fabricante: this.peca.fabricante,
        modeloCarro: this.peca.modeloCarro,

      }).subscribe({
        next: () => {
          this.router.navigate(['/pecas']);
          alert('Peça editada com sucesso!');
        },
        error: (error: any) => {
          console.error('Erro ao editar o carro:', error);
          alert('Erro ao editar a peça. Por favor, tente novamente mais tarde.');
        }
      });

    } else {
      // Salvar novo carro
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
    this.peca = new Pecas();
  }

}
