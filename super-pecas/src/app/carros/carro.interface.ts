// import { Carros } from '../models/Carros';
export interface CarrosResponse {
  totalRecords: number;
  totalElements: number;
  content: Carro[];
}

export interface Carro {
  carroId: number;
  nomeModelo: string;
  fabricante: string;
  codigoUnico: string;
}
