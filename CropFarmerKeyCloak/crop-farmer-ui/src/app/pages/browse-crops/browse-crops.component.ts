import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseCropsService } from '../../service/services/BrowseCrops/browse-crops.service';
import { UserCartService } from '../../service/services/UserCart/user-cart.service';
import { AuthService } from '../../service/services/auth.service';

@Component({
  selector: 'app-browse-crops',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './browse-crops.component.html',
  styleUrls: ['./browse-crops.component.css']
})
export class BrowseCropsComponent implements OnInit {

  crops: any[] = [];
  userEmailId!: string;

  constructor(
    private browseCropService: BrowseCropsService,
    private cartService: UserCartService,
    private auth: AuthService
  ) {}

ngOnInit() {
  this.auth.email$.subscribe(email => {
    if (email) {
      this.userEmailId = email;

      this.loadCropsWithCart();   // depends on email
    }
  });

  // crops list does NOT depend on email
  this.browseCropService.getAllCrops().subscribe(data => {
    this.crops = data.map((crop: any) => ({
      ...crop,
      cartQuantity: 0
    }));
  });
}

   loadCropsWithCart() {
    this.browseCropService.getAllCrops().subscribe(crops => {

      this.cartService.getCart(this.userEmailId).subscribe(cartItems => {

        const cartCropIds = new Set(
          (cartItems || []).map((item: any) => item.cropId)
        );

        this.crops = crops.map((crop: any) => ({
          ...crop,
          addedToCart: cartCropIds.has(crop.cropId)
        }));

      });

    });
  }

  addToCart(crop: any) {
    this.cartService
      .addToCart(crop.cropId, 1, this.userEmailId)
      .subscribe(() => {
        crop.addedToCart = true;
      },
    );
  }
}
