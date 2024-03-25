import { Component, OnInit } from '@angular/core';
import { Carros } from '../models/Carros';
import { CarrosService } from './carros.service';
import { ApiService } from '../../../services/api.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

interface CarrosPaginadosResponse {
  content: Carros[];
  // Adicione outras propriedades, se necessário
}

@Component({
  selector: 'app-carros',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent implements OnInit {
  carros: Carros[] = []; // Array de objetos Carros
  carrosSelecionados: any;
  cols: any[]|undefined;
updateCarro: any;
deleteCarro: any;


  // Exemplo de como formatar a exibição da lista de carros em uma tabela
  formatarListaCarros(): string {
    let tabela = "IdNome do ModeloFabricanteCódigo ÚnicoAção\n";

    this.carros.forEach((carro) => {
      tabela += `${carro.carroId} ${carro.NomeModelo} ${carro.Fabricante} ${carro.CodigoUnico} Ação\n`;
    });

    return tabela;
  }
  constructor(private carrosService: CarrosService) { }

  ngOnInit() {
    const page = 0;
    const size = 10;
    this.carrosService.getCarrosPaginados(page, size)
      .pipe(
        map((response: any) => {
          if (response && response.content) {
            return response.content as Carros[];
          } else {
            console.error('Invalid API response format:', response);
            return [];
          }
        })
      )
      .subscribe({
        next: (content: Carros[]) => {
          this.carros = content;
          console.log(this.carros);
        },
        error: (error) => {
          console.error('Error fetching carros:', error);
        }
      });

  }
}
