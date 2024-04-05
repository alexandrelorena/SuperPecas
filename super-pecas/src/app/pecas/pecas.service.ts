import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Peca } from '../models/Pecas';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { PecasResponse } from '../models/Pecas';
import { Top10CarroComMaisPecas } from '../models/top10carros';

interface PecaUpdateData {
  pecaId: number;
  nome?: string,
  descricao?: string,
  numeroSerie?: string,
  fabricante?: string,
  modeloCarro?: string,
  carroId: 0
}

@Injectable({
  providedIn: 'root'
})
export class PecasService {
  private baseUrl = environment.host + '/peca';
  getTodasPecas: any;

  constructor(private http: HttpClient) { }

  getPecas() {
      return this.http.get<Peca[]>('/peca');
  }

  getPeca(pecaId: number) {
      return this.http.get<Peca>(`${environment.host}/peca/${pecaId}`);
  }

  getAllPecas(page: number, size: number): Observable<PecasResponse> {
    const url = `${environment.host}/peca/listaTodosPaginado?page=${page}&size=${size}`;
    return this.http.get<PecasResponse>(url);
  }

  getPecasByTermo(termo: String, page: number = 0) {
  return this.http.get<Peca[]>(`${environment.host}/peca/listaTodosPaginado/${termo}?page=${page}&size=10`);
  }

  createPeca(peca: Peca): Observable<Peca> {
    return this.http.post<Peca>(`${this.baseUrl}`, peca);
  }

  gravarPeca(pecaData: Peca): Observable<Peca> {
    return this.http.post<Peca>(`${this.baseUrl}`, pecaData);
  }

  updatePeca(pecaData: PecaUpdateData): Observable<Peca> {
    return this.http.put<Peca>(`${this.baseUrl}`, pecaData);
  }

  deletePeca(pecaId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${pecaId}`);
  }

  getTop10CarroComMaisPecas(){
    return this.http.get<Top10CarroComMaisPecas[]>(`${environment.host}/peca/listaTop10CarroComMaisPecas`);
  }
}
