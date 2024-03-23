import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pecas } from '../models/Pecas';

@Injectable({
  providedIn: 'root'
})
export class PecasService {

  constructor(private http: HttpClient) { }

  getPecas() {
    return this.http.get<Pecas[]>('api/pecas');
  }

  getPeca(pecaID: number) {
    return this.http.get<Pecas>('api/pecas/' + pecaID);
  }

  createPeca(peca: Pecas) {
    return this.http.post<Pecas>('api/pecas', peca);
  }

  updatePeca(peca: Pecas) {
    return this.http.put<Pecas>('api/pecas/' + peca.PecaID, peca);
  }

  deletePeca(pecaID: number) {
    return this.http.delete('api/pecas/' + pecaID);
  }
}
