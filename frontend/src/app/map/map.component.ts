import {
  AfterViewInit,
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
} from '@angular/core';
import * as L from 'leaflet';
import { BehaviorSubject, TimeInterval } from 'rxjs';
import { catchError, map, switchAll } from 'rxjs/operators';
import { HttpUtilService } from '../services/http-util.service';
import { MqttDataService } from '../services/mqtt-data.service';
import { groupBy } from 'lodash';
import { SideNavControlService } from '../services/side-nav-control.service';
import { environment } from '../../environments/environment';

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
export class MapComponent implements AfterViewInit, OnInit, OnDestroy {

  private map: any;
  showSideNav: boolean = false;
  pollInterval!: any;
  deviceMessages: BehaviorSubject<any> = new BehaviorSubject({});
  deviceInfo!: any;
  private mapMarkersLayerGroup: any = L.layerGroup();
  private markersLayer: any = L.control.layers();

  private initMap(): void {
    this.map = L.map('map', {
      center: [-1.286389, 36.817223],
      zoom: 13,
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

    this.mapMarkersLayerGroup = this.mapMarkersLayerGroup.addTo(this.map);

    this.mapMarkersLayerGroup.addLayer(marker);

    var overlay = { markers: this.mapMarkersLayerGroup };
    this.markersLayer = L.control.layers(undefined, overlay).addTo(this.map);

    marker
      .on('click', () => {
        this.showOrHideSideNav();
        this.recenterMap(-1.286389, 36.817223);
      })
      .bindPopup(`<p>An Initial Test Marker<br/>Wait for Markers to Load</p>`);
  }

  private updateMarkers(data: any) {
    // this.mapMarkersLayerGroup =  L.layerGroup();
    this.mapMarkersLayerGroup.clearLayers();

    Object.keys(data).forEach((singleDataKey) => {
      const marker = L.marker([
        data[singleDataKey][0]?.latitude,
        data[singleDataKey][0]?.longitude,
      ]);

      marker
        .on('click', () => {
          // this.showOrHideSideNav();
          this.recenterMap(
            data[singleDataKey][0]?.latitude,
            data[singleDataKey][0]?.longitude
          );
          this.sideNavControlService.clickMarker(
            this.deviceInfo[singleDataKey][0],
            data[singleDataKey]
          );
        })
        .bindPopup(
          `<p>Name: <b>${this.deviceInfo[singleDataKey][0]?.name}</b><br/>battery: <b>${this.deviceInfo[singleDataKey][0]?.batteryLevel}</b><br/>humidity: <b>${data[singleDataKey][0]?.humidity}</b> <br/>Temp: <b>${data[singleDataKey][0]?.temp}</b></p>`
        );
      // this.mapMarkersLayerGroup = this.mapMarkersLayerGroup.addTo(this.map);
      this.mapMarkersLayerGroup.addLayer(marker);
    });

    var overlay = { markers: this.mapMarkersLayerGroup };
    this.markersLayer = L.control.layers(undefined, overlay).addTo(this.map);
  }

  private recenterMap(lat: number, number: number): void {
    this.map.flyTo(new L.LatLng(lat, number));
  }

  constructor(
    private mqttDataService: MqttDataService,
    private httpUtilService: HttpUtilService,
    private sideNavControlService: SideNavControlService
  ) {}

  ngOnInit(): void {
    this.httpUtilService
      .makeHttpRequest('GET', `${environment.apiUrl}/device/all`)
      .subscribe({
        next: (next) => {
          this.deviceInfo = groupBy(
            next.data,
            (single: any) => single.deviceUuid
          );
          // let data = groupBy(next.data, (data: any) => data.deviceId);
          // this.deviceMessages.next(data);
        },
        error: (error) => console.log(error),
      });

    this.sideNavControlService.showOrHideNav.subscribe((data) => {
      this.showSideNav = data;
    });
    // this.mqttDataService.connect();
  }

  ngAfterViewInit(): void {
    this.initMap();

    this.pollInterval = setInterval(() => {
      this.httpUtilService
        .makeHttpRequest('GET', `${environment.apiUrl}/device-data/all`)
        .subscribe({
          next: (next) => {
            let data = groupBy(next.data, (data: any) => data.deviceId);
            this.deviceMessages.next(data);
          },
          error: (error) => console.log(error),
        });
    }, 10000);

    this.deviceMessages.subscribe((data: object) => {
      if (Object.keys(data).length > 0) {
        this.updateMarkers(data);
      }
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.pollInterval);
  }
  
  showOrHideSideNav() {
    this.showSideNav = !this.showSideNav;
  }
}
