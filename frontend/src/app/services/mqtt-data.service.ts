import { Injectable } from '@angular/core';
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
  
  connection !: WebSocket

  constructor(){
    this.connection =  Stomp.client('ws://localhost:1883/ws');
    this.connection.onopen =(event) => {
      console.log(event)
    }
    this.connection.onmessage = (event) => {
      console.log(event);
    }
    this.connection.onerror =(err) => {
      console.log(err);
    }

    this.connection.onclose =(event) => {
      console.log(event)
    }
  }
  deviceMessages: BehaviorSubject<any> = new BehaviorSubject({});
  private getNewConnection = () => {
    return
  };

  connect = () => {
    console.log(' in connect');
   
   
  };

  handleError<T>() {
    return (error: any): Observable<any> => {
      let res: any = error;
      return of(res);
    };
  }
}
