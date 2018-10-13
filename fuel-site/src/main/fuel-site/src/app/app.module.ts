import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { GasStationPriceService } from "./services/gasStationPrice/gasStationPrice.service";
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "./app-routing.module";
import { ChartsModule } from "ng2-charts";
import { GasStationService } from "./services/gasStation/gasStation.service";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { FuelMapComponent } from './fuelmap/fuel-map/fuel-map.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FuelMapComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ChartsModule,
    NgbModule.forRoot()
  ],
  providers: [
    GasStationPriceService,
    GasStationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
