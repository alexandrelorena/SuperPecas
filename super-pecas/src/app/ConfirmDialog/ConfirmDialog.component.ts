import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { CarrosService } from '../carros/carros.service';
import { SuccessDialogComponent } from '../SuccessDialog/SuccessDialog.component';
import { ErrorDialogComponent } from '../ErrorDialog/ErrorDialog.component';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './ConfirmDialog.component.html',
  styleUrls: ['./ConfirmDialog.component.css']
})
export class ConfirmDialogComponent implements OnInit {

  message: string;
  carroId: any;
  nomeModelo: any;
  carrosSubscription: any;


  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private carrosService: CarrosService,
    public dialog: MatDialog

  ) {
    this.message = data;
    this.carroId = data.carroId;
    this.nomeModelo = data.nomeModelo;
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
          this.dialog.open(SuccessDialogComponent, {
            width: '300px',
            data: { message: 'Carro excluído com sucesso!' }
          });
          setTimeout(() => {
            location.reload();
          }, 1000);
          this.dialogRef.close(true);
        },
        error: (error: any) => {
          console.error('Erro ao excluir o carro:', error);
          this.dialogRef.close(false);this.dialog.open(ErrorDialogComponent, {
            width: '300px',
            data: { message: 'Erro ao excluir o carro!' }!
          });
        }
      });
  }
  ngOnDestroy() {
    if (this.carrosSubscription) {
      this.carrosSubscription.unsubscribe();
    }
  }
}
