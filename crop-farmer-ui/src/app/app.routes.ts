import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AddCropComponent } from './pages/add-crop/add-crop.component';
import { MyCropsComponent } from './pages/my-crops/my-crops.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { farmerGuard } from './guards/farmer.guard';
import { LoginComponent } from './pages/login/login.component';
import { BrowseCropsComponent } from './pages/browse-crops/browse-crops.component';
import { CartComponent } from './pages/cart/cart.component';
import { userGuard } from './guards/user.guard';

export const routes: Routes = [
   { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },

  // Farmer-only pages
  { path: 'add-crop', component: AddCropComponent, canActivate: [farmerGuard] },
  { path: 'my-crops', component: MyCropsComponent, canActivate: [farmerGuard] },

  // Orders accessible to all logged-in users
  { path: 'orders', component: OrdersComponent },

  // User-only pages
  { path: 'browse-crops', component: BrowseCropsComponent, canActivate: [userGuard] },
  { path: 'cart', component: CartComponent, canActivate: [userGuard] },

  { path: '**', redirectTo: 'login' }
];

