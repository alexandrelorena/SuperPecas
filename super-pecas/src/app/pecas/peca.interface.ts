// import { Carros } from '../models/Carros';
export interface PecasResponse {
  totalRecords: number;
  totalElements: number;
  content: Peca[];
}

export interface Peca {
  pecaId: number;
  nome: string;
  descricao: string;
  numeroSerie: string;
  fabricante: string;
  modeloCarro: string;
  carroId: number;
}
