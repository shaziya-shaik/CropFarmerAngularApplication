import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../service/services/OrderService/order-service.service';
import { AuthService } from '../../service/services/auth.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: any[] = [];
  userEmailId!: string;

  constructor(
    private orderService: OrderService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    this.userEmailId = this.auth.getUsername()!;
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrders(this.userEmailId).subscribe({
      next: (data) => this.orders = data,
      error: () => this.orders = []
    });
  }

cancelOrder(orderId: number) {
  if (!confirm('Are you sure you want to cancel this order?')) {
    return;
  }

  // 🔥 UI update FIRST (instant)
  const order = this.orders.find(o => o.orderId === orderId);
  if (order) {
    order.orderStatus = 'CANCELLED';
  }

  this.orderService.cancelOrder(orderId).subscribe({
    next: () => {
      // optional: reload to be 100% sure
      // this.loadOrders();
    },
    error: () => {
      alert('Failed to cancel order');
      // rollback if needed
      if (order) {
        order.orderStatus = 'PROCESSING';
      }
    }
  });
}


}
