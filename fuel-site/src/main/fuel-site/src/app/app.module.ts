import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { GasStationPriceService } from "./services/gasStationPrice/gasStationPrice.service";
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "./app-routing.module";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    GasStationPriceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
