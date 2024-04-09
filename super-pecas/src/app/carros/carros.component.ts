import { Component, OnInit } from '@angular/core';
import { Carro } from '../models/Carros';
import { CarrosService } from './carros.service';
import { CarrosResponse } from './carro.interface';
import { MatDialog } from '@angular/material/dialog';
import { GerenciaCarrosComponent } from '../gerenciaCarros/gerenciaCarros.component';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from '../ConfirmDialog/ConfirmDialog.component';
import { ErrorDialogComponent } from '../ErrorDialog/ErrorDialog.component';


@Component({
  selector: 'app-carros',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})


export class CarrosComponent implements OnInit {
  carros: Carro[] = [];
  filteredCarros: Carro[] = [];
  searchText: string = '';
  isEditMode: boolean = false;
  totalRecords = 0;
  first = 0;
  rows = 10;
  carrosSubscription: any;
  carro: Carro;
  carroEditFormVisible: { [key: number]: boolean } = {};
  displayEditDialog: boolean = false;
  displayAddDialog: boolean = false;
  currentPage = 0;
  searchTerm = '';
  totalPages: number = 0;
  hideAddDialog: any;
    novoCarro: Carro = {
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
    this.carro = {} as Carro;
  }

  ngOnInit() {
    this.loadCarros();
  }
  loadCarros(page: number = this.currentPage, size: number = this.rows) {
    const searchTerm = this.searchText.trim().toLowerCase();
    if (searchTerm) {
      this.carrosService.getCarrosByTermo(searchTerm, page, size)
        .subscribe((response: CarrosResponse) => {
          this.filteredCarros = response.content;
          this.totalRecords = response.totalElements;
          this.currentPage = page;
          this.searchTerm = searchTerm;
        });
    } else {
      this.carrosService.getAllCarros(page, size)
        .subscribe((response: CarrosResponse) => {
          this.carros = response.content;
          this.totalRecords = response.totalElements;
          this.filteredCarros = [...this.carros];
          this.currentPage = page;
          this.searchTerm = '';
        });
    }
  }

  onPageChange(event: any) {
    const page = event.first / event.rows;
    this.loadCarros(page);
  }

  openAddForm() {
    this.router.navigate(['/gerenciar-carros']);
  }

  openEditForm(carroId: Carro) {
    console.log(carroId);
    this.isEditMode = true;
    this.router.navigate(['/gerenciar-carros', carroId]);
  }

  hideEditDialog() {
    this.displayEditDialog = false;
    this.displayAddDialog = false;
  }

  showMessage(message: string) {
    alert(message);
  }

  salvarCarro() {

    if (!this.novoCarro.nomeModelo || !this.novoCarro.fabricante || !this.novoCarro.codigoUnico) {
      this.showMessage('Todos os campos devem ser preenchidos.');
      return;
    }

    this.carrosService.createCarro(this.novoCarro)
      .subscribe({
        next: () => {
          console.log('Carro criado com sucesso!');

          this.limparCampos();
          this.loadCarros();
        },
        error: (error: any) => {
          console.error('Erro ao criar o carro:', error);
        }
      });
  }

  limparCampos() {

    this.novoCarro = {
      nomeModelo: '',
      fabricante: '',
      codigoUnico: '',
      carroId: 0
    };
  }

  openConfirmDialog(carroId: number, nomeModelo: string): void {
    this.carrosService.verificarPecasAssociadas(carroId).subscribe({
      next: (temPecasAssociadas: boolean) => {
        if (temPecasAssociadas) {
          this.dialog.open(ErrorDialogComponent, {
            width: '400px',
            data: { message: 'Este carro possui peças associadas e não pode ser excluído.' }
          });
        } else {
          const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '600px',
            data: { message: 'Tem certeza de que deseja excluir esta peça?', carroId: carroId, nomeModelo: nomeModelo}
          });

          dialogRef.afterClosed().subscribe((result: any) => {
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
      },
      error: (error: any) => {
        console.error('Erro ao verificar peças associadas:', error);
        this.dialog.open(ErrorDialogComponent, {
          width: '400px',
          data: { message: 'Exclua as peças associadas ao carro.' }
        });
      }
    });
  }

  // excluirCarro(carroId: number) {
  //   this.carrosService.deleteCarro(carroId).subscribe({
  //     next: () => {
  //       console.log('Carro excluído com sucesso!');
  //       setTimeout(() => {
  //         location.reload();
  //       }, 1000);
  //     },
  //     error: (error) => {
  //       console.error('Erro ao excluir o carro:', error);
  //     }
  //   });
  // }

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

  onSearchKeyPress(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.onSearchChange();
    }
  }

  onSearchChange() {

  this.loadCarros(0);

  }
    ngOnDestroy() {
    if (this.carrosSubscription) {
      this.carrosSubscription.unsubscribe();
    }
  }
}
