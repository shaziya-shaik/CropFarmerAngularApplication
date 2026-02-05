import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AddCropComponent } from './pages/add-crop/add-crop.component';
import { MyCropsComponent } from './pages/my-crops/my-crops.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { farmerGuard } from './guards/farmer.guard';

import { BrowseCropsComponent } from './pages/browse-crops/browse-crops.component';
import { CartComponent } from './pages/cart/cart.component';
import { userGuard } from './guards/user.guard';

export const routes: Routes = [
   { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },

  // Farmer-only pages
  { path: 'add-crop', component: AddCropComponent },
  { path: 'my-crops', component: MyCropsComponent },

  // Orders accessible to all logged-in users
  { path: 'orders', component: OrdersComponent },

  // User-only pages
  { path: 'browse-crops', component: BrowseCropsComponent },
  { path: 'cart', component: CartComponent },

  { path: '**', redirectTo: 'login' }
];

