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
    return webSocket('ws://localhost:12345');
  };

  connect = () => {
    let messages = this.getNewConnection().pipe(
      map((res) => {
        let newRes: any = res;

        return newRes;
      }),
      catchError(this.handleError<any>())
    );

    this.deviceMessages.next(messages);
  };

  handleError<T>() {
    return (error: any): Observable<any> => {
      let res: any = error;
      return of(res);
    };
  }
}
