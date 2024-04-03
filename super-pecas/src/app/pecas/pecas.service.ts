import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pecas } from '../models/Pecas';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { PecasResponse } from './peca.interface';

interface PecaUpdateData {
  pecaId: number;
  nome?: string,
  descricao?: string,
  numeroSerie?: string,
  fabricante?: string,
  modeloCarro?: string,
  // carroId: 0
}

@Injectable({
  providedIn: 'root'
})
export class PecasService {
  private baseUrl = environment.apiUrl + '/peca';
  addPeca: any;

  constructor(private http: HttpClient) { }

  getPecas() {
    return this.http.get<Pecas[]>('/peca');
  }

  getPeca(pecaId: number) {
    return this.http.get<Pecas>('/peca/' + pecaId);
  }

  getAllPecas(page: number, size: number): Observable<PecasResponse> {
    const url = `${this.baseUrl}/listaTodosPaginado?page=${page}&size=${size}`;
    return this.http.get<PecasResponse>(url);
  }

  createPeca(peca: Pecas): Observable<Pecas> {
    return this.http.post<Pecas>(`${this.baseUrl}`, peca);
  }

  updatePeca(pecaData: PecaUpdateData): Observable<Pecas> {
    return this.http.put<Pecas>(`${this.baseUrl}`, pecaData);
  }

  deletePeca(pecaId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${pecaId}`);
  }
}
