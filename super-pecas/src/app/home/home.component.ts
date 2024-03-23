import { Component, OnInit } from '@angular/core';

import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],

})
export class HomeComponent implements OnInit {
  Top10Fabricantes: any;
  CarrosePecas: any;


  constructor() { }

  ngOnInit() {

    this.Top10Fabricantes = {
      title: {
        text: ""
      },
      data: [{
        type: "pie",
        dataPoints: [
          { label: "Fab1",  y: 20  },
          { label: "Fab2", y: 25  },
          { label: "Fab3", y: 35  },
          { label: "Fab4",  y: 40  },
          { label: "Fab5",  y: 38  },
          { label: "Fab6",  y: 20  },
          { label: "Fab7", y: 25  },
          { label: "Fab8", y: 35  },
          { label: "Fab9",  y: 40  },
          { label: "Fab10",  y: 38  }
        ]
      }]
    };

    this.CarrosePecas = {
      title: {
        text: ""
      },

  axisX: {
    labelAngle: 135
    },
    axisY: {
    title: "Quantidade"
    },
      data: [{
        type: "column",
        dataPoints: [
          { label: "CarroPeças1",  y: 20  },
          { label: "CarroPeças2", y: 25  },
          { label: "CarroPeças3", y: 35  },
          { label: "CarroPeças4",  y: 40  },
          { label: "CarroPeças5",  y: 38  },
          { label: "CarroPeças6",  y: 20  },
          { label: "CarroPeças7", y: 25  },
          { label: "CarroPeças8", y: 35  },
          { label: "CarroPeças9",  y: 40  },
          { label: "CarroPeças10",  y: 38  }
        ]
      }]
    };

  }
}
