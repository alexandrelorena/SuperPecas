import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Carros } from './../models/Carros';
import { CarrosResponse } from './carro.interface';

@Injectable({
  providedIn: 'root'
})
export class CarrosService {
  private baseUrl = 'http://localhost:8080/carro';

  constructor(private http: HttpClient) { }

  getAllCarros(page: number, size: number): Observable<CarrosResponse> {
    const url = `${this.baseUrl}/listaTodosPaginado?page=${page}&size=${size}`;
    return this.http.get<CarrosResponse>(url);
  }

  createCarro(carro: Carros): Observable<Carros> {
    return this.http.post<Carros>(`${this.baseUrl}/create`, carro);
  }

  updateCarro(carroID: number, carro: Carros): Observable<Carros> {
    return this.http.put<Carros>(`${this.baseUrl}/update/${carroID}`, carro);
  }

  deleteCarro(carroID: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${carroID}`);
  }
}
