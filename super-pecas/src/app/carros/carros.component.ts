import { Carros } from '../models/Carros';
import { Component, OnInit } from '@angular/core';
import { CarrosService } from './carros.service'; // Importando o CarrosService (supondo que esteja na mesma pasta)

@Component({
  selector: 'app-carros',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent implements OnInit {
  carros: Carros[] | null = null; // Definindo carros como um array de Carros ou null para melhor segurança de tipo
  Carros!: typeof Carros;

  constructor(private carrosService: CarrosService) { } // Injetando CarrosService

  ngOnInit() {
    this.carrosService.getCarros()
      .subscribe(carros => this.Carros = Carros);
  }
}
