import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { Injectable } from "@angular/core";
import { GasStation } from "./gasStation";

@Injectable()
export class GasStationService  {
  constructor(private http: HttpClient) {
  }

  getGasStations(countryCode: string, province: string): Observable<GasStation[]> {
    let url = "/api/gasStation";

    const options = {
      params: new HttpParams()
        .set("countryCode", countryCode)
        .append("province", province)
    };

    return this.http.get<GasStation[]>(url, options);
  }
}
