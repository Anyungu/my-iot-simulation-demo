import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { catchError, tap, switchAll, map } from 'rxjs/operators';
import {
  BehaviorSubject,
  EMPTY,
  Observable,
  of,
  Subject,
  Subscription,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MqttDataService {
  deviceMessages: BehaviorSubject<any> = new BehaviorSubject({});
  private getNewConnection = (): WebSocketSubject<any> => {
    return webSocket('ws://localhost:15000/ws');
  };

  connect = () => {
    this.getNewConnection().subscribe( res => {
      console.log(res)
      this.deviceMessages.next(res);
    })
  };

  handleError<T>() {
    return (error: any): Observable<any> => {
      let res: any = error;
      return of(res);
    };
  }
}
