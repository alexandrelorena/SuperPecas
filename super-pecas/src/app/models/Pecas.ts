import { Carro } from './Carros';

export interface Peca {
  // carroFabricante: any;
  // carroNomeModelo: any;
  carroId: any;
  pecaId: number;
  nome: string;
  descricao: string;
  numeroSerie: string;
  fabricante: string;
  modeloCarro: string;
  // carro: Carro;
}

export interface PecasResponse {
  totalRecords: number;
  totalElements: number;
  content: Peca[];
}
