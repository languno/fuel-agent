import { Component, OnInit, ViewChild } from '@angular/core';
import { GasStationPriceService } from "../services/gasStationPrice/gasStationPrice.service";
import { GasStationPrice } from "../services/gasStationPrice/gasStationPrice";
import { GasStationService } from "../services/gasStation/gasStation.service";
import { GasStationWithPrice } from "../services/gasStation/gasStationWithPrice";
import { FuelMapComponent } from "../fuelmap/fuel-map/fuel-map.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  @ViewChild(FuelMapComponent) fuelMap: FuelMapComponent;

  gasStationsWithPrice: GasStationWithPrice[];
  selectedGasStation: GasStationWithPrice;

  lineChartData:Array<any> = new Array<any>();
  lineChartLabels: Array<String> = new Array<String>();
  lineChartOptions:any = {
    responsive: true,
    scales: {
      yAxes: [{
        type: 'linear',
        ticks: {
          callback: function (value, index, values) {
            if (value.length) {
              return Number(value).toFixed(3);
            }
            return value;
          }
        },
      }]
    }
  };
  lineChartColors:Array<any> = [
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // blue
      backgroundColor: 'rgba(66, 99, 255,0.2)',
      borderColor: 'rgba(66, 99, 255,1)',
      pointBackgroundColor: 'rgba(66, 99, 255,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(66, 99, 255,0.8)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];

  lineChartLegend: boolean = true;
  lineChartType: string = 'line';

  constructor(private gasStationPriceService: GasStationPriceService,
              private gasStationService: GasStationService) { }

  ngOnInit() {
    this.getGasStations();
  }

  getGasStations(): void {
    this.gasStationService.getGasStationsWithPrice("ES", "Malaga").subscribe(
      gasStationList => {
        this.gasStationsWithPrice = gasStationList;
        this.fuelMap.setMarker(gasStationList);
      });
  }

  getPrice(gasStationId: number): void {
    this.gasStationPriceService.getPrice(gasStationId, 14).subscribe(
      priceList => this.updateChart(priceList));
  }

  updateChart(gasStationPriceList: GasStationPrice[]): void {
    let _lineChartData:Array<any> = new Array(3);

    _lineChartData[0] = { data: new Array(gasStationPriceList.length), label: "Diesel" };
    _lineChartData[1] = { data: new Array(gasStationPriceList.length), label: "Gasoline 95" };
    _lineChartData[2] = { data: new Array(gasStationPriceList.length), label: "Gasoline 98" };
    this.lineChartLabels = new Array<String>(gasStationPriceList.length);

    for (let i = 0; i < gasStationPriceList.length; i++) {
      _lineChartData[0].data[i] = parseFloat(gasStationPriceList[i].dieselPrice.split(" ")[0]);
      _lineChartData[1].data[i] = gasStationPriceList[i].gasoline95Price.split(" ")[0];
      _lineChartData[2].data[i] = gasStationPriceList[i].gasoline98Price.split(" ")[0];
      this.lineChartLabels[i] = gasStationPriceList[i].dateTime;
    }
    this.lineChartData = _lineChartData;
  }

  selectGasStation(gasStationWithPrice: GasStationWithPrice) {
    this.selectedGasStation = gasStationWithPrice;
    this.getPrice(gasStationWithPrice.gasStation.id);
  }

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }
}
