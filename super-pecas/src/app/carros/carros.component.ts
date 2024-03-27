
import { Component, OnInit } from '@angular/core';
import { Carros } from '../models/Carros';
import { CarrosService } from './carros.service';
// import { Observable } from 'rxjs';
// import { ApiService } from '../../../services/api.service';
import { CarrosResponse } from './carro.interface';

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

  constructor(
    private carrosService: CarrosService
  ) { }

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
      this.totalRecords = response.totalRecords;

      console.log('Carros:', this.carros);
    });
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.loadCarros();
  }
  updateCarro(carroID: number, carro: Carros) {
    this.carrosService.updateCarro(carroID, carro)
      .subscribe({
        next: () => {
          console.log('Carro atualizado com sucesso!');
        },
        error: (error) => {
          console.error('Erro ao atualizar o carro:', error);
        }
      });
  }

  deleteCarro(carroID: number) {
    this.carrosService.deleteCarro(carroID)
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
