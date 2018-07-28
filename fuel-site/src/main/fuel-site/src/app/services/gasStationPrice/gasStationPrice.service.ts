import { Injectable } from '@angular/core';
import { Observable }     from 'rxjs';
import { GasStationPrice } from "./gasStationPrice";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class GasStationPriceService  {
  constructor(private http: HttpClient) {
  }

  getPrice(gasStationId: number): Observable<GasStationPrice[]> {
    let url = "/api/gasStationPrice/" + gasStationId;

    return this.http.get<GasStationPrice[]>(url);
  }
}
