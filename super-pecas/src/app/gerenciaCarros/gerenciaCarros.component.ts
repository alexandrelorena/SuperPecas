import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Carro } from '../models/Carros';
import { CarrosService } from '../carros/carros.service';

@Component({
  selector: 'app-gerencia-carros',
  templateUrl: './gerenciaCarros.component.html',
  styleUrls: ['./gerenciaCarros.component.css']
})
export class GerenciaCarrosComponent implements OnInit {
  carro: Carro = {} as Carro;
  isEditing: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private carrosService: CarrosService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const carroId = params['carroId'];
      if (carroId) {
        // Editar
        this.isEditing = true;
        this.carrosService.getCarro(carroId).subscribe((carro: Carro) => {
          this.carro = carro;
        });
      } else {
        // Cadastrar
        this.isEditing = false;
        this.carro = {} as Carro;
      }
    });
  }

  salvarCarro(): void {
    // Verificar se todos os campos estão preenchidos
    if (!this.carro.nomeModelo || !this.carro.fabricante || !this.carro.codigoUnico) {
      alert('Todos os campos devem ser preenchidos.');
      return;
    }

    if (this.isEditing) {

      this.carrosService.updateCarro({
        carroId: this.carro.carroId,
        nomeModelo: this.carro.nomeModelo,
        fabricante: this.carro.fabricante,
        codigoUnico: this.carro.codigoUnico
       }).subscribe({
        next: () => {
          this.router.navigate(['/carros']);
          alert('Carro editado com sucesso!');
        },
        error: (error: any) => {
          console.error('Erro ao editar o carro:', error);
          alert('Erro ao editar o carro. Por favor, tente novamente mais tarde.');
        }
      });

    } else {

      this.carrosService.createCarro(this.carro).subscribe({
        next: () => {
          this.router.navigate(['/cadastrar-carro']);
          alert('Carro cadastrado com sucesso!');
          setTimeout(() => {
            location.reload();
          }, 1000);
        },
        error: (error: any) => {
          console.error('Erro ao cadastrar o carro:', error);
          alert('Ocorreu um erro ao cadastrar o carro. Por favor, tente novamente mais tarde.');
        }
      });
    }
  }

  voltarParaCarros() {
    this.router.navigate(['/carros']);
  }

  limparCampos() {
    this.carro = {} as Carro;
  }

}
