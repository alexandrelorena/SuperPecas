import { Component, OnInit } from '@angular/core';
import { NotificationType, NotificationsService } from 'angular2-notifications';
import { Subject, takeUntil } from 'rxjs';
import { CarrosService } from '../carros/carros.service';
import { PecasService } from '../pecas/pecas.service';
import { Top10CarroComMaisPecas } from '../models/top10carros';
import { Top10Fabricantes } from '../models/top10fabricantes';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private unsubscribe = new Subject<void>;

  top10fabricantes!: Top10Fabricantes[];
  top10CarroComMaisPecas!: Top10CarroComMaisPecas[];

  data!: any;
  options: any;

  chartOptionsPie: any;
  chartOptionsColumn: any;
  _reloadPie = false;
  _reloadColumn = false;

  constructor(private carrosService: CarrosService, private pecasService: PecasService, private _notifications: NotificationsService) {

  }

  ngOnInit(): void {
    this.getTop10Fabricantes();
    this.getTop10PecasCarros();
  }


  getTop10Fabricantes() {
    this.carrosService.getTop10Fabricantes()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe({
        next: (response) => {
          this.top10fabricantes = response;
          this.callPieChart(this.top10fabricantes);
        },
        error: (error) => {
          this._notifications.create("Erro", error.error, NotificationType.Error);
        }
      });
  }


  getTop10PecasCarros() {
    this.pecasService.getTop10CarroComMaisPecas()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe({
        next: (response) => {
          this.top10CarroComMaisPecas = response;
          this.callColumnChart(this.top10CarroComMaisPecas);
        },
        error: (error) => {
          this._notifications.create("Erro", error.error, NotificationType.Error);
        }
      });
  }

  callPieChart(top10fabricantes: Top10Fabricantes[]) {
    const data = top10fabricantes.map((x) => {
      return { y: x.quantidade, label: x.fabricante };
    });

    this.chartOptionsPie = {
      animationEnabled: true,
      animationDuration: 500,
      data: [{
        type: "pie",
        indexLabel: "{label}  #percent%",
        percentFormatString: "#0.##",
        dataPoints: data
      }]
    };

    this._reloadPie = true;

    setTimeout(() => {
      this.removeElementsByClass("canvasjs-chart-credit");
    }, 100);
  }

  callColumnChart(top10CarroComMaisPecas: Top10CarroComMaisPecas[]) {
    const data = top10CarroComMaisPecas.map((x) => {
      return { y: x.quantidade, label: x.carro };
    });

    this.chartOptionsColumn = {
      animationEnabled: true,
      animationDuration: 500,
      theme: "light2", // "light1", "light2", "dark1", "dark2"
      axisY: {
        title: "Quantidade",
        interval: 1,
        padding: 20
      },
      axisX: {
        interval: 1,
        labelFontSize: 10,
      },
      data: [{
        type: "column",
        showInLegend: false,
        dataPoints: data,
        interval: 1
      }]
    };

    this._reloadColumn = true;

    setTimeout(() => {
      this.removeElementsByClass("canvasjs-chart-credit");
    }, 10);
  }

  removeElementsByClass(className: string) {
    const elements = document.getElementsByClassName(className);
    while (elements.length > 0) {
      elements[0].parentNode!.removeChild(elements[0]);
    }
  }

}
