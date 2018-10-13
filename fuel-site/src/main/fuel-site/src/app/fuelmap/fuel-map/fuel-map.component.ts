import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

declare var ol: any;

@Component({
  selector: 'fuel-map',
  templateUrl: './fuel-map.component.html',
  styleUrls: ['./fuel-map.component.css']
})
export class FuelMapComponent implements OnInit, OnChanges {

  @Input()
  latitude: number;

  @Input()
  longitude: number;

  map: any;

  constructor() { }

  ngOnInit() {
    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        })
      ],
      view: new ol.View({
        center:
          ol.proj.fromLonLat([13.736168, 51.054102]),
        zoom: 8
      })
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    this.setMapCenter();
  }

  setMapCenter(): void {

    if (!this.latitude || !this.longitude) {
      return;
    }

    var lat = parseFloat("" + this.latitude);
    var lon = parseFloat("" + this.longitude);

    var view = this.map.getView();
    view.setCenter(ol.proj.fromLonLat([lon, lat]));
    view.setZoom(12);
  }
}
