import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCartService } from '../../service/services/UserCart/user-cart.service';
import { AuthService } from '../../service/services/auth.service';
import { OrderService } from '../../service/services/OrderService/order-service.service';
import { Router } from '@angular/router';
import { filter, take } from 'rxjs/operators';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems: any[] = [];
  userEmailId!: string;
  isPlacingOrder = false;

  constructor(
    private cartService: UserCartService,
    private auth: AuthService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit() {
    this.auth.email$
      .pipe(
        filter(email => !!email),
        take(1) // ✅ only once
      )
      .subscribe(email => {
        this.userEmailId = email!;
        this.loadCart();
      });
  }

  loadCart() {
    this.cartService.getCart(this.userEmailId).subscribe({
      next: data => this.cartItems = data || [],
      error: () => this.cartItems = []
    });
  }

  removeItem(item: any) {
    this.cartService
      .deleteItem(item.cartItemId, this.userEmailId)
      .subscribe(() => {
        this.cartItems = this.cartItems.filter(
          i => i.cartItemId !== item.cartItemId
        );
      });
  }

  increase(item: any) {
    this.cartService
      .addToCart(item.cropId, 1, this.userEmailId)
      .subscribe({
        next: () => this.loadCart(),
        error: err =>
          alert(err.error?.message || 'Not enough stock available')
      });
  }

  decrease(item: any) {
    this.cartService
      .deleteItem(item.cartItemId, this.userEmailId)
      .subscribe(() => this.loadCart());
  }

  getTotal() {
    return this.cartItems.reduce(
      (sum, item) => sum + item.quantity * item.price,
      0
    );
  }

  clearCart() {
    this.cartService.clearCart(this.userEmailId).subscribe(() => {
      this.cartItems = [];
    });
  }

  placeOrder() {
    if (this.cartItems.length === 0 || this.isPlacingOrder) return;

    this.isPlacingOrder = true;

    this.orderService.placeOrder(this.userEmailId).subscribe({
      next: () => {
        alert('✅ Order placed successfully');

        // clean backend cart
        this.cartService.clearCart(this.userEmailId).subscribe();

        this.cartItems = [];
        this.router.navigate(['/orders']);
        this.isPlacingOrder = false;
      },
      error: err => {
        alert(err.error?.message || 'Failed to place order');
        this.isPlacingOrder = false;
      }
    });
  }
}
