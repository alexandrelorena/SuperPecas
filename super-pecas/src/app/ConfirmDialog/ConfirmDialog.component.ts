import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CarrosService } from '../carros/carros.service';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './ConfirmDialog.component.html',
  styleUrls: ['./ConfirmDialog.component.css']
})
export class ConfirmDialogComponent implements OnInit {

  message: string;
  carroId: any;
  loadCarros: any;
openConfirmDialog: any;

  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private carrosService: CarrosService
  ) {
    this.message = data;
    this.carroId = data.carroId;
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  deleteCarro(carroId: number) {
    this.carrosService.deleteCarro(carroId)
      .subscribe({
        next: () => {
          console.log('Carro excluído com sucesso!');
          setTimeout(() => {
            location.reload();
          }, 1000);
          this.dialogRef.close(true); // Feche o diálogo com um valor verdadeiro para indicar sucesso
        },
        error: (error: any) => {
          console.error('Erro ao excluir o carro:', error);
          this.dialogRef.close(false); // Feche o diálogo com um valor falso para indicar falha
        }
      });
  }
}
