import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserCartService {

  private API_URL = 'http://localhost:8080/cart';

  constructor(private http: HttpClient) {}

  // ✅ ADD / UPDATE CART ITEM
  addToCart(cropId: number, quantity: number, userEmailId: string) {
  const payload = { cropId, quantity };

  return this.http.post(
    `${this.API_URL}/cartItems`,
    payload,
    { params: { userEmailId } }

    
  );
}

  // ✅ GET CART
  getCart(userEmailId: string) {
    return this.http.get<any>(
      `${this.API_URL}/cartItems`,
      { params: { userEmailId } }
      
    )
    .pipe(
       map(response => response.items) 
    );
  }

  // ✅ DELETE / DECREASE ITEM
  deleteItem(cartItemId: number, userEmailId: string) {
    return this.http.delete(
      `${this.API_URL}/deleteItem/${cartItemId}`,
      { params: { userEmailId } }
    );
  }

  // ✅ CLEAR CART
  clearCart(userEmailId: string) {
  return this.http.delete(
    `${this.API_URL}/DeleteCart`,
    { params: { userEmailId } }
  );
}
}
