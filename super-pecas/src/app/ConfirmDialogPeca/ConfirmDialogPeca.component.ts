import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PecasService } from '../pecas/pecas.service';
@Component({
  selector: 'app-ConfirmDialogPeca',
  templateUrl: './ConfirmDialogPeca.component.html',
  styleUrls: ['./ConfirmDialogPeca.component.css']
})
export class ConfirmDialogPecaComponent implements OnInit {

  message: string;
  pecaId: any;
  loadPecas: any;
  openConfirmDialogPeca: any;
  nome: any;
  peca: any;

constructor(
  public dialogRef: MatDialogRef<ConfirmDialogPecaComponent>,
  @Inject(MAT_DIALOG_DATA) public data: any,
  private pecasService: PecasService
) {
  this.message = data;
  this.pecaId = data.pecaId;
  this.nome = data.nome;
}

ngOnInit(): void {
}

onNoClick(): void {
  this.dialogRef.close();
}

  deletePeca(pecaId: number) {
    this.pecasService.deletePeca(pecaId)
      .subscribe({
        next: () => {
          console.log('Peça excluída com sucesso!');
          setTimeout(() => {
            location.reload();
          }, 1000);
          this.dialogRef.close(true);
        },
        error: (error: any) => {
          console.error('Erro ao excluir a peça:', error);
          this.dialogRef.close(false);
        }
      });
  }
}
