import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carros } from './../models/Carros';
import { CarrosResponse } from './carro.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CarrosService {
  // private baseUrl = 'http://4.228.57.162:8080/carro';
  private baseUrl = environment.apiUrl + '/carro';
  addCarro: any;

  constructor(private http: HttpClient) { }

  verificarPecasAssociadas(carroId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/${carroId}/verificar-pecas-associadas`);
  }


  getAllCarros(page: number, size: number): Observable<CarrosResponse> {
    const url = `${this.baseUrl}/listaTodosPaginado?page=${page}&size=${size}`;
    return this.http.get<CarrosResponse>(url);
  }

  createCarro(carro: Carros): Observable<Carros> {
    return this.http.post<Carros>(`${this.baseUrl}/`, carro);
  }

  updateCarro(carroId: number, carro: Carros): Observable<Carros> {
    return this.http.put<Carros>(`${this.baseUrl}/${carroId}`, carro);
  }

  deleteCarro(carroId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${carroId}`);
  }
}
