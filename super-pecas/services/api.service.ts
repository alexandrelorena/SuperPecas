import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CarrosResponse } from '../src/app/carros/carro.interface';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/carro/listaTodos'; // Remova "/listaTodos" do baseUrl

  constructor(private http: HttpClient) { }

  // Método para buscar todos os carros
  getAllCarros(page: number, size: number): Observable<CarrosResponse> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get<CarrosResponse>(url);
  }


  // private baseUrl = 'http://localhost:8080/carro/listaTodos';

  // constructor(private http: HttpClient) { }

  // // Método para buscar carros
  // getCarros(page: number, size: number): Observable<CarrosResponse> {
  //   return this.http.get<Carros[]>(`${this.baseUrl}?page=${page}&size=${size}`);
  // }

  getCarro(carroID: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/carro/${carroID}`);
  }


  createCarro(carro: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/carro`, carro);
  }


  // Método para atualizar um carro existente
  updateCarro(carroID: number, carro: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/carro/${carroID}`, carro);
  }

  deleteCarro(carroID: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/carro/${carroID}`);
  }
}
