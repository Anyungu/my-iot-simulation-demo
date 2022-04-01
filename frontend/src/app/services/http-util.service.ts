import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class HttpUtilService {
  constructor(private readonly httpClient: HttpClient) {}

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   */
  private handleError<T>() {
    return (error: any): Observable<any> => {
      let res: any = {
        message: error.message,
        status: error.status,
      };
      return of(res);
    };
  }

  /**
   * A generic function to make any/all http request
   * @param method Request Method
   * @param url Requst URL
   * @param options Any Options
   * @returns
   */
  makeHttpRequest(method: string, url: string, options?: {}): Observable<any> {
    return this.httpClient.request<any>(method, url, options).pipe(
      map((res) => {
        let newRes: any = {
          message: 'success',
          status: 200,
          data: res,
        };

        return newRes;
      }),
      catchError(this.handleError<any>())
    );
  }
}
