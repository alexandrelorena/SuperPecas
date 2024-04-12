import { Carro } from '../models/Carros';
export interface CarrosResponse {
  totalRecords: number;
  totalElements: number;
  content: Carro[];
}

export interface Carros {
  carroId: number;
  nomeModelo: string;
  fabricante: string;
  codigoUnico: string;
  carro: Carro;
}
