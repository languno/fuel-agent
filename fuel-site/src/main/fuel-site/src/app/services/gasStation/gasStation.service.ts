import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { Injectable } from "@angular/core";
import { GasStationWithPrice } from "./gasStationWithPrice";

@Injectable()
export class GasStationService  {
  constructor(private http: HttpClient) {
  }

  getGasStationsWithPrice(countryCode: string, province: string): Observable<GasStationWithPrice[]> {
    let url = "/api/gasStation";

    const options = {
      params: new HttpParams()
        .set("countryCode", countryCode)
        .append("province", province)
    };

    return this.http.get<GasStationWithPrice[]>(url, options);
  }
}
