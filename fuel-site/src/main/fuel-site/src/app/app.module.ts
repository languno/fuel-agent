import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { GasStationPriceService } from "./services/gasStationPrice/gasStationPrice.service";
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "./app-routing.module";
import { ChartsModule } from "ng2-charts";
import { GasStationService } from "./services/gasStation/gasStation.service";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ChartsModule
  ],
  providers: [
    GasStationPriceService,
    GasStationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
