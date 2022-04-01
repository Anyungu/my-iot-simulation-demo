import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as L from 'leaflet';

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41],
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements AfterViewInit {
  private map: any;
  showSideNav: boolean = true;

  private initMap(): void {
    this.map = L.map('map', {
      center: [-1.286389, 36.817223],
      zoom: 9,
    });

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    tiles.addTo(this.map);

    const marker = L.marker([-1.286389, 36.817223]);

    marker.addTo(this.map);

    marker.on('click', () => {
      this.showOrHideSideNav();
      this.recenterMap();
    });
  }

  private recenterMap(): void {
    this.map.flyTo(new L.LatLng(-1.286389, 36.817223));
  }

  constructor() {} 

  ngAfterViewInit(): void {
    this.initMap();
  }

  showOrHideSideNav() {
    this.showSideNav = !this.showSideNav;
  }
}
