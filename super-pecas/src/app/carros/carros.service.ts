import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Carros } from '../models/Carros';

@Injectable({
  providedIn: 'root'
})
export class CarrosService {

  constructor(private http: HttpClient) { }

  getCarros() {
    return this.http.get<Carros[]>('api/carros');
  }

  getCarro(carroID: number) {
    return this.http.get<Carros>('api/carros/' + carroID);
  }

  createCarro(carro: Carros) {
    return this.http.post<Carros>('api/carros', carro);
  }

  updateCarro(carro: Carros) {
    return this.http.put<Carros>('api/carros/' + carro.CarroID, carro);
  }

  deleteCarro(carroID: number) {
    return this.http.delete('api/carros/' + carroID);
  }
}
