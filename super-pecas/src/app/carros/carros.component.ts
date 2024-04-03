
import { Component, OnInit } from '@angular/core';
import { Carros } from '../models/Carros';
import { CarrosService } from './carros.service';
import { CarrosResponse } from './carro.interface';
import { MatDialog } from '@angular/material/dialog';
import { GerenciaCarrosComponent } from '../gerenciaCarros/gerenciaCarros.component';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from '../ConfirmDialog/ConfirmDialog.component';


@Component({
  selector: 'app-carros',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})


export class CarrosComponent implements OnInit {
  carros: Carros[] = [];
  filteredCarros: Carros[] = [];
  searchText: string = '';
  isEditMode: boolean = false;
  totalRecords = 0;
  first = 0;
  rows = 10;
  carrosSubscription: any;
  carro: Carros;
  carroEditFormVisible: { [key: number]: boolean } = {};
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
    private router: Router,
    private dialog: MatDialog
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
      this.filteredCarros = [...this.carros];

      console.log('Carros:', this.carros);
    });
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.loadCarros();
  }

  openAddForm() {
    this.router.navigate(['/gerenciar']);
  }

  openEditForm(carroId: Carros) {
    console.log(carroId);
    this.isEditMode = true;
    this.router.navigate(['/gerenciar', carroId]);
  }

  hideEditDialog() {
    this.displayEditDialog = false;
    this.displayAddDialog = false;
  }

  showMessage(message: string) {
    alert(message);
  }

  salvarCarro() {
    // Verifica se os campos estão vazios
    if (!this.novoCarro.nomeModelo || !this.novoCarro.fabricante || !this.novoCarro.codigoUnico) {
      this.showMessage('Todos os campos devem ser preenchidos.');
      return;
    }

    // Se todos os campos estiverem preenchidos, continua com a operação de salvar
    this.carrosService.createCarro(this.novoCarro)
      .subscribe({
        next: () => {
          console.log('Carro criado com sucesso!');
          // Limpa os campos e recarrega a lista de carros
          this.limparCampos();
          this.loadCarros();
        },
        error: (error: any) => {
          console.error('Erro ao criar o carro:', error);
        }
      });
  }

  limparCampos() {
    // Limpa os campos
    this.novoCarro = {
      nomeModelo: '',
      fabricante: '',
      codigoUnico: '',
      carroId: 0
    };
  }

  openConfirmDialog(carroId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '250px',
      data: { message: 'Tem certeza de que deseja excluir este carro?', carroId: carroId } // Certifique-se de passar o objeto de dados corretamente
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.carrosService.deleteCarro(carroId)
          .subscribe({
            next: () => {
              console.log('Carro excluído com sucesso!');
              setTimeout(() => {
                location.reload();
              }, 1000);
            },
            error: (error) => {
              console.error('Erro ao excluir o carro:', error);
            }
          });
      }
    });
  }

  filterCarros() {
    if (this.searchText.trim()) {
      this.filteredCarros = this.carros.filter(carro =>
        carro.nomeModelo.toLowerCase().includes(this.searchText.toLowerCase()) ||
        carro.fabricante.toLowerCase().includes(this.searchText.toLowerCase()) ||
        carro.codigoUnico.toLowerCase().includes(this.searchText.toLowerCase())
      );
    } else {
      this.filteredCarros = this.carros;
    }
  }
  onSearchChange() {
    this.filterCarros();
  }
    ngOnDestroy() {
    if (this.carrosSubscription) {
      this.carrosSubscription.unsubscribe();
    }
  }
}
