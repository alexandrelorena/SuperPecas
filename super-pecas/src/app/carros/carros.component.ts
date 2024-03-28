
import { Component, OnInit } from '@angular/core';
import { Carros } from '../models/Carros';
import { CarrosService } from './carros.service';
import { CarrosResponse } from './carro.interface';
import { MatDialog } from '@angular/material/dialog';
import { EditarCarroComponent } from './editar-carro/editar-carro.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-carros',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent implements OnInit {
  carros: Carros[] = [];
  totalRecords = 0;
  first = 0;
  rows = 10;
  carrosSubscription: any;
  carro: Carros;
  carroEditFormVisible: { [key: number]: boolean } = {};
  dialog: any;
  displayEditDialog: boolean = false;
  displayAddDialog: boolean = false;
  hideAddDialog: any;
    novoCarro: Carros = {
    nomeModelo: '',
    fabricante: '',
    codigoUnico: '',
    carroId: 0
  };

  constructor(
    private carrosService: CarrosService,
    private router: Router
  ) {
    this.carro = {} as Carros;
  }

  ngOnInit() {
    this.loadCarros();
  }

  loadCarros() {
    const page = this.first / this.rows;
    const size = this.rows;

    this.carrosService.getAllCarros(page, size)
    .subscribe((response: CarrosResponse) => {
      console.log('Dados recebidos do serviço:', response);
      this.carros = response.content;
      this.totalRecords = response.totalElements;

      console.log('Carros:', this.carros);
    });
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.loadCarros();
  }

  openAddForm() {
    // Exibir o diálogo de adição de carro
    this.displayAddDialog = true;

    // Chamar o serviço para criar o carro
    this.carrosService.createCarro(this.novoCarro)
      .subscribe({
        next: (response: any) => {
          // Verificar se a resposta foi bem-sucedida
          if (response && response.status === 200) {
            // Se o carro foi adicionado com sucesso, feche o diálogo e exiba a mensagem
            this.displayAddDialog = false;
            this.showMessage('Carro adicionado com sucesso!');
          } else {
            // Se houve um problema, exiba uma mensagem de erro
            console.error('Erro ao adicionar o carro:', response);
            this.showMessage('Erro ao adicionar o carro. Por favor, tente novamente mais tarde.');
          }
        },
        error: (error: any) => {
          // Se ocorrer um erro durante a solicitação, exiba uma mensagem de erro
          console.error('Erro ao adicionar o carro:', error);
          this.showMessage('Erro ao adicionar o carro. Por favor, tente novamente mais tarde.');
        }
      });
  }
  openEditForm(carro: Carros) {
    this.carro = { ...carro };
    this.carroEditFormVisible[carro.carroId] = true;
    this.displayEditDialog = true;
  }

  hideEditDialog() {
    this.displayEditDialog = false;
    this.displayAddDialog = false;
  }

  updateCarro(carroId: number, carro: Carros) {
    this.carrosService.updateCarro(carroId, carro)
      .subscribe({
        next: () => {
          this.hideEditDialog();
          this.showMessage('Carro atualizado com sucesso!');
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        },
        error: (error) => {
          console.error('Erro ao atualizar o carro:', error);
        }
      });
  }

  showMessage(message: string) {
    alert(message);
  }

  deleteCarro(carroId: number) {
    this.carrosService.deleteCarro(carroId)
      .subscribe({
        next: () => {
          console.log('Carro excluído com sucesso!');
          this.loadCarros();
        },
        error: (error) => {
          console.error('Erro ao excluir o carro:', error);
        }
      });
  }

    ngOnDestroy() {
    if (this.carrosSubscription) {
      this.carrosSubscription.unsubscribe();
    }
  }
}
