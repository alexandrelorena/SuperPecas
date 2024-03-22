import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Carros } from '../models/Carros';


@Injectable({
  providedIn: 'root'
})
export class CarrosService {

  constructor(private http: HttpClient) { }

  getCarros() {
    return this.http.get('api/carros');
  }

  getCarro(id: number) {
    return this.http.get('api/carros/' + id);
  }

  createCarro(carro: Carros) {
    return this.http.post('api/carros', carro);
  }

  updateCarro(carro: Carros) {
    return this.http.put('api/carros/' + carro.id, carro);
  }

  deleteCarro(id: number) {
    return this.http.delete('api/carros/' + id);
  }
}
