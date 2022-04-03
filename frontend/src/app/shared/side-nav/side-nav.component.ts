import { Component, OnInit } from '@angular/core';
import { SingleDeviceRecord } from 'src/app/models/device-data';
import { SideNavControlService } from 'src/app/services/side-nav-control.service';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css'],
})
export class SideNavComponent implements OnInit {
  public readings: any[] = [];

  public deviceInfo:any = null

  constructor(private sideNavControlService: SideNavControlService) {}

  ngOnInit(): void {
    this.sideNavControlService.sideNavContent.subscribe((data) => {
      this.readings = data?.deviceData;
      this.deviceInfo = data?.deviceInfo;
    });
    // for (let i = 0; i < 40; i++) {
    //   const singleDeviceRecord: SingleDeviceRecord = {
    //     lat: -12434.23432,
    //     long: 12434.23432,
    //     temp: 21,
    //     humidity: 2.9,
    //     timestamp: '22-03-2022',
    //   };
    //   this.readings.push({
    //     ...singleDeviceRecord,
    //   });
    // }
  }

  closeNav() {
    this.sideNavControlService.clickX()
  }
}
