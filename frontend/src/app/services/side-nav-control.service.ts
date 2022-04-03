import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SideNavControlService {
  sideNavContent: BehaviorSubject<any> = new BehaviorSubject(null);

  showOrHideNav: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() {}

  clickMarker(deviceInfo: any, deviceData: any) {
    this.showOrHideNav.next(true);
    let data = { deviceInfo: deviceInfo, deviceData: deviceData }
    this.sideNavContent.next(data);
  }

  clickX() {
    this.showOrHideNav.next(false);
    this.sideNavContent.next(null);
  }
}
