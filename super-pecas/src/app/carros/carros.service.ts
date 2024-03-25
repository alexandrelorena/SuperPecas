import { Injectable } from '@angular/core';
import { Carros } from '../models/Carros';
import { Observable } from 'rxjs';
import { ApiService } from '../.././../services/api.service';

@Injectable({
  providedIn: 'root'
})
export class CarrosService {

  constructor(private apiService: ApiService) { }

  getCarrosPaginados(page: number, size: number): Observable<Carros[]> {
    return this.apiService.getCarrosPaginados(page, size);
  }

  createCarro(carro: Carros): Observable<Carros> {
    return this.apiService.createCarro(carro);
  }

  updateCarro(carroID: number, carro: Carros): Observable<Carros> {
    return this.apiService.updateCarro(carroID, carro);
  }
  deleteCarro(carroID: number): Observable<void> {
    return this.apiService.deleteCarro(carroID);
  }
}
