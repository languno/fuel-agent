import { Component, OnInit } from '@angular/core';
import { GasStationPriceService } from "../services/gasStationPrice/gasStationPrice.service";
import { GasStationPrice } from "../services/gasStationPrice/gasStationPrice";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  gasStationPriceList: GasStationPrice[];

  constructor(private gasStationPriceService: GasStationPriceService) { }

  ngOnInit() {
    this.getPrice(13025);
  }

  getPrice(gasStationId: number): void {

    this.gasStationPriceService.getPrice(gasStationId).subscribe(
      priceList => this.gasStationPriceList = priceList);
  }

}
