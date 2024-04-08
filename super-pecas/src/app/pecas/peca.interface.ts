import { Peca } from '../models/Pecas';
export interface PecasResponse {
  totalRecords: number;
  totalElements: number;
  content: Peca[];
}
export interface Pecas {
  pecaId: number;
  nome: string;
  descricao: string;
  numeroSerie: string;
  fabricante: string;
  modeloCarro: string;
  carroId: number;
}
