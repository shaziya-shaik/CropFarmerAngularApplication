import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUrl = 'http://localhost:8080/order';

  constructor(private http: HttpClient) {}

  placeOrder(userEmailId: string): Observable<void> {
    const params = new HttpParams().set('userEmailId', userEmailId);
    return this.http.post<void>(`${this.baseUrl}/placeOrder`, null, { params });
  }

  getOrders(userEmailId: string): Observable<any[]> {
    const params = new HttpParams().set('userEmailId', userEmailId);
    return this.http.get<any[]>(`${this.baseUrl}/getOrderItems`, { params });
  }

  cancelOrder(orderId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/cancel/${orderId}`, {});
  }
}
