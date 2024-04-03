import { Component, NgModule, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Importe o FormsModule
import { ActivatedRoute, Router } from '@angular/router';
import { Carros } from '../../models/Carros';
import { CarrosService } from '../../carros/carros.service';

@Component({
  selector: 'app-editar-carro',
  templateUrl: './editar-carro.component.html',
  styleUrls: ['./editar-carro.component.css']
})
export class EditarCarroComponent implements OnInit {
  carro: Carros;
voltarParaCarros: any;

  constructor(private router: Router, private route: ActivatedRoute, private carrosService: CarrosService) {
    this.carro = new Carros();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const carroId = params['carroId'];
      this.carrosService.getCarro(carroId).subscribe((carro: Carros) => {
        this.carro = carro;
      });
    });
  }

  updateCarro(carroId: number, carro: Carros) {
    this.carrosService.updateCarro(carroId, carro)
      .subscribe({
        next: () => {
          console.log('Carro atualizado com sucesso!');
          // Aqui você pode redirecionar para onde desejar ou tomar outra ação após a atualização do carro
        },
        error: (error) => {
          console.error('Erro ao atualizar o carro:', error);
        }
      });
  }
}

@NgModule({
  imports: [FormsModule], // Adicione FormsModule aos imports
  declarations: [EditarCarroComponent]
})
export class EditarCarroModule { }
