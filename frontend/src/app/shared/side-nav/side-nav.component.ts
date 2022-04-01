import { Component, OnInit } from '@angular/core';
import { SingleDeviceRecord } from 'src/app/models/device-data';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css'],
})
export class SideNavComponent implements OnInit {
  public readings: SingleDeviceRecord[] = [];

  constructor() {}

  ngOnInit(): void {
    for (let i = 0; i < 40; i++) {
      const singleDeviceRecord: SingleDeviceRecord = {
        lat: -12434.23432,
        long: 12434.23432,
        temp: 21,
        humidity: 2.9,
        timestamp: '22-03-2022',
      };
      this.readings.push({
        ...singleDeviceRecord,
      });
    }
  }
}
