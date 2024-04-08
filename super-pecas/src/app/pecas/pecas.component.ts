import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { NotificationType, NotificationsService } from 'angular2-notifications';
import { PecasResponse } from './peca.interface';
import { Peca } from '../models/Pecas';
import { PecasService } from './pecas.service';
import { Carros } from './../carros/carro.interface';
import { ConfirmDialogPecaComponent } from '../ConfirmDialogPeca/ConfirmDialogPeca.component';

@Component({
  selector: 'app-pecas',
  templateUrl: './pecas.component.html',
  styleUrl: './pecas.component.css'
})
export class PecasComponent implements OnInit {
  pecas: Peca[] = [];
  filteredPecas: Peca[] = [];
  searchText: string = '';
  isEditMode: boolean = false;
  totalRecords = 0;
  first = 0;
  rows = 10;
  pecasSubscription: any;
  peca: Peca;
  pecaEditFormVisible: { [key: number]: boolean } = {};
  displayEditDialog: boolean = false;
  displayAddDialog: boolean = false;
  currentPage = 0;
  searchTerm = '';
  totalPages: number = 0;
  hideAddDialog: any;
  Carro!: Carros;
    novaPeca: Peca = {
      nome: '',
      descricao: '',
      numeroSerie: '',
      fabricante: '',
      modeloCarro: '',
      pecaId: 0,
      carroId: 0
    };

constructor(
  private pecasService: PecasService,
  private router: Router,
  private dialog: MatDialog
) {
  this.peca = {} as Peca;
}

ngOnInit() {
  this.loadPecas();
}

loadPecas(page: number = this.currentPage, size: number = this.rows) {
  if (this.searchText.trim()) {
    this.pecasService.getPecasByTermo(this.searchText.trim(), page, size)
      .subscribe((response: PecasResponse) => {
        this.filteredPecas = response.content;
        this.totalRecords = response.totalElements;
        this.currentPage = page;
        this.searchTerm = this.searchTerm;
      });
  } else {
    this.pecasService.getAllPecas(page, size)
      .subscribe((response: PecasResponse) => {
        this.filteredPecas = response.content;
        this.totalRecords = response.totalElements;
        this.currentPage = page;
      });
  }
}

onPageChange(event: any) {
  const page = event.first / event.rows;
  this.loadPecas(page);
}

openAddForm() {
  this.router.navigate(['/gerenciar-pecas']);
}

openEditForm(pecaId: number) {
  console.log(pecaId);
  this.isEditMode = true;
  this.router.navigate(['/gerenciar-pecas',pecaId]);
}

hideEditDialog() {
  this.displayEditDialog = false;
  this.displayAddDialog = false;
}

showMessage(message: string) {
  alert(message);
}

salvarPeca() {
  if (!this.novaPeca.nome || !this.novaPeca.descricao || !this.novaPeca.numeroSerie || !this.novaPeca.fabricante || !this.novaPeca.modeloCarro) {
    this.showMessage('Todos os campos devem ser preenchidos.');
    return;
  }

    this.pecasService.createPeca(this.novaPeca)
    .subscribe({
      next: () => {
        console.log('Peça criada com sucesso!');
        this.limparCampos();
        this.loadPecas();
      },
      error: (error: any) => {
        console.error('Erro ao criar a peça:', error);
      }
    });
}

limparCampos() {
  this.novaPeca = {
    pecaId: 0,
    nome: '',
    descricao: '',
    numeroSerie: '',
    fabricante: '',
    modeloCarro: '',
    carroId: 0,
  };
}

openConfirmDialogPeca(pecaId: number, nome: string): void {
  const dialogRef = this.dialog.open(ConfirmDialogPecaComponent, {
    width: '600px',
    data: { message: 'Tem certeza de que deseja excluir esta peça?', pecaId: pecaId, nome: nome}
  });

  dialogRef.afterClosed().subscribe((result: any) => {
    if (result) {
      this.pecasService.deletePeca(pecaId)
        .subscribe({
          next: () => {
            console.log('Peça excluída com sucesso!');
            setTimeout(() => {
              location.reload();
            }, 1000);
          },
          error: (error) => {
            console.error('Erro ao excluir a peça:', error);
          }
        });
    }
  });
}

filterPecas() {
  if (this.searchText.trim()) {
    this.filteredPecas = this.pecas.filter(peca =>
      peca.nome.toLowerCase().includes(this.searchText.toLowerCase()) ||
      peca.descricao.toLowerCase().includes(this.searchText.toLowerCase()) ||
      peca.numeroSerie.toLowerCase().includes(this.searchText.toLowerCase()) ||
      peca.fabricante.toLowerCase().includes(this.searchText.toLowerCase()) ||
      peca.modeloCarro.toLowerCase().includes(this.searchText.toLowerCase())
    );
  } else {
    this.filteredPecas = this.pecas;
  }
}

onSearchKeyPress(event: KeyboardEvent) {
  if (event.key === 'Enter') {
    event.preventDefault();
   this.onSearchChange();
  }
}
onSearchChange() {
   this.loadPecas(0);
}

ngOnDestroy() {
  if (this.pecasSubscription) {
    this.pecasSubscription.unsubscribe();
  }
}
}
