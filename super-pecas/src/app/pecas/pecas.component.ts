
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Carros } from '../models/Carros';
import { PecasResponse } from './peca.interface';
import { Pecas } from '../models/Pecas';
import { PecasService } from './pecas.service';
import { ConfirmDialogComponent } from '../ConfirmDialog/ConfirmDialog.component';

@Component({
  selector: 'app-pecas',
  templateUrl: './pecas.component.html',
  styleUrl: './pecas.component.css'
})
export class PecasComponent implements OnInit {
  pecas: Pecas[] = [];
  filteredPecas: Pecas[] = [];
  searchText: string = '';
  isEditMode: boolean = false;
  totalRecords = 0;
  first = 0;
  rows = 10;
  pecasSubscription: any;
  peca: Pecas;
  pecaEditFormVisible: { [key: number]: boolean } = {};
  displayEditDialog: boolean = false;
  displayAddDialog: boolean = false;
  hideAddDialog: any;
  novaPeca: Pecas = {
    pecaId: 0,
    nome: '',
    descricao: '',
    numeroSerie: '',
    fabricante: '',
    modeloCarro: '',
    carroId: 0
  };

constructor(
  private pecasService: PecasService,
  private router: Router,
  private dialog: MatDialog
) {
  this.peca = {} as Pecas;
}

ngOnInit() {
  this.loadPecas();
}

loadPecas() {
  const page = this.first / this.rows;
  const size = this.rows;

  this.pecasService.getAllPecas(page, size)
  .subscribe((response: PecasResponse) => {
    console.log('Dados recebidos do serviço:', response);
    this.pecas = response.content;
    this.totalRecords = response.totalElements;
    this.filteredPecas = [...this.pecas];

    console.log('Pecas:', this.pecas);
  });
}

onPageChange(event: any) {
  this.first = event.first;
  this.loadPecas();
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
  // Verifica se os campos estão vazios
  if (!this.novaPeca.nome || !this.novaPeca.descricao || !this.novaPeca.numeroSerie || !this.novaPeca.fabricante || !this.novaPeca.modeloCarro) {
    this.showMessage('Todos os campos devem ser preenchidos.');
    return;
  }

  // Se todos os campos estiverem preenchidos, continua com a operação de salvar
  this.pecasService.createPeca(this.novaPeca)
    .subscribe({
      next: () => {
        console.log('Peça criada com sucesso!');
        // Limpa os campos e recarrega a lista de carros
        this.limparCampos();
        this.loadPecas();
      },
      error: (error: any) => {
        console.error('Erro ao criar a peça:', error);
      }
    });
}

limparCampos() {
  // Limpa os campos
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

openConfirmDialog(pecaId: number): void {
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    width: '250px',
    data: { message: 'Tem certeza de que deseja excluir esta peça?', pecaId: pecaId } // Certifique-se de passar o objeto de dados corretamente
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
onSearchChange() {
  this.filterPecas();
}
  ngOnDestroy() {
  if (this.pecasSubscription) {
    this.pecasSubscription.unsubscribe();
  }
}
}
