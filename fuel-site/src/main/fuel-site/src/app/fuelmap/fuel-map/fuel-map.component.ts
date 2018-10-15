import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { GasStationWithPrice } from "../../services/gasStation/gasStationWithPrice";

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
  markerSource: any;
  markerStyle: any;
  selectedMarkerStyle: any;

  constructor() { }

  ngOnInit() {

    this.markerSource = new ol.source.Vector();

    this.markerStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'https://openlayers.org/en/v4.6.4/examples/data/icon.png',
        crossOrigin: 'anonymous'
      }))
    });

    this.selectedMarkerStyle = new ol.style.Style({
      image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'https://openlayers.org/en/v4.6.4/examples/data/icon.png',
        crossOrigin: 'anonymous',
        color: '#FF0000'
      }))
    });

    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        }),
        new ol.layer.Vector({
          source: this.markerSource,
          style: this.markerStyle,
        }),
      ],
      view: new ol.View({
        center:
          ol.proj.fromLonLat([13.736168, 51.054102]),
        zoom: 8
      })
    });

    this.addHoverHandler();
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
    view.setZoom(13);

    this.clearAllSelectedMarker();
    this.selectMarker(lat, lon);
  }

  setMarker(gasStationsWithPrice: GasStationWithPrice[]): void {

    this.markerSource.clear();

    gasStationsWithPrice.forEach((gasStationWithPrice: GasStationWithPrice) => {
      var lat = parseFloat(gasStationWithPrice.gasStation.latitude);
      var lon = parseFloat(gasStationWithPrice.gasStation.longitude);

      let dieselPrice: string = gasStationWithPrice.currentPrice != null ? gasStationWithPrice.currentPrice.dieselPrice : "";
      let gasoline95Price: string = gasStationWithPrice.currentPrice != null ? gasStationWithPrice.currentPrice.gasoline95Price : "";
      let brand: string = gasStationWithPrice.gasStation.brand;

      var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326',
          'EPSG:3857')),
        brand: brand,
        dieselPrice: dieselPrice,
        gasoline95Price: gasoline95Price
      });

      this.markerSource.addFeature(iconFeature);
    });
  }

  private selectMarker(lat, lon) {
    var iconFeature = this.markerSource.getClosestFeatureToCoordinate(ol.proj.fromLonLat([lon, lat]));
    iconFeature.setStyle(this.selectedMarkerStyle);
  }

  private clearAllSelectedMarker(): void {
    this.markerSource.forEachFeature((iconFrature: any) => {
      iconFrature.setStyle(this.markerStyle);
    });
  }

  private addHoverHandler(): void {

    var selectPointerMove = new ol.interaction.Select({
      condition: ol.events.condition.pointerMove
    });

    this.map.addInteraction(selectPointerMove);

    selectPointerMove.on('select', function(e) {

      if (e.selected.length > 0) {
        var iconFeature = e.selected[0];
        console.log('selected ' + iconFeature.get('brand') + ': diesel price ' + iconFeature.get('dieselPrice') + ', gasoline: ' + iconFeature.get('gasoline95Price'));
      }

      if (e.deselected.length > 0) {
        var iconFeature = e.deselected[0];
        console.log('deselected ' + iconFeature.get('brand') + ': diesel price ' + iconFeature.get('dieselPrice') + ', gasoline: ' + iconFeature.get('gasoline95Price'));
      }
    });
  }
}
