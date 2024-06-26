import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carro } from './../models/Carros';
import { CarrosResponse } from './carro.interface';
import { environment } from '../../environments/environment';
import { Top10Fabricantes } from '../models/top10fabricantes';

interface CarroUpdateData {
  carroId: number;
  nomeModelo?: string;
  fabricante?: string;
  codigoUnico?: string;
}

@Injectable({
  providedIn: 'root'
})

export class CarrosService {
  // private baseUrl = 'http://4.228.57.162:8080/carro';
  private baseUrl = environment.host + '/carro';
  addCarro: any;

  constructor(private http: HttpClient) {}

  getCarro(carroId: number) {
    return this.http.get<Carro>(`${this.baseUrl}/${carroId}`);
  }

  verificarPecasAssociadas(carroId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/${carroId}/verificar-pecas-associadas`);
  }

  getAllCarros(page: number, size: number): Observable<CarrosResponse> {
    const url = `${this.baseUrl}/listaTodosPaginado?page=${page}&size=${size}`;
    return this.http.get<CarrosResponse>(url);
  }

  getTodosCarros() {
    return this.http.get<Carro[]>(`${this.baseUrl}/listaTodos`);
}

  getCarrosByTermo(termo: string, page: number = 0, size: number = 10): Observable<CarrosResponse> {
    const url = `${this.baseUrl}/listaTodosPaginado/${termo}?page=${page}&size=${size}`;
    return this.http.get<CarrosResponse>(url);
}

  createCarro(carro: Carro): Observable<Carro> {
    return this.http.post<Carro>(`${this.baseUrl}`, carro);
  }

  updateCarro(carro: Carro): Observable<Carro> {
    return this.http.put<Carro>(`${this.baseUrl}`, carro);
  }

  deleteCarro(carroId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${carroId}`);
  }

  getTodosFabricantes() {
    return this.http.get<String[]>(`${environment.host}/carro/listaTodosFabricantes`);
}

  getTop10Fabricantes(){
    return this.http.get<Top10Fabricantes[]>(`${environment.host}/carro/listaTop10Fabricantes`);
}

}
