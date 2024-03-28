import { Component } from '@angular/core';

import { Pecas } from '../models/Pecas';
import { PecasService } from './pecas.service';
// import { PecasResponse } from './peca.interface';
import { MatDialog } from '@angular/material/dialog';

import { Router } from '@angular/router';

@Component({
  selector: 'app-pecas',
  templateUrl: './pecas.component.html',
  styleUrl: './pecas.component.css'
})
export class PecasComponent {
pecas: any;
totalRecords = 0;
first = 0;
rows = 10;
pecasSubscription: any;
peca: any;
// peca: Pecas;
// pecaEditFormVisible: { [key: number]: boolean } = {};
// dialog: any;
// displayEditDialog: boolean = false;
// displayAddDialog: boolean = false;
// hideAddDialog: any;
//   novaPeca: Pecas = {
//   nome: '',
//   descricao: '',
//   NumeroSerie: '',
//   fabricante: '',
//   ModeloCarro: '',
//   carroId: 0
};
