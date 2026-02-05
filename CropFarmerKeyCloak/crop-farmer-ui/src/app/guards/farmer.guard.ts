import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const farmerGuard: CanActivateFn = () => {
  const router = inject(Router);
  const role = localStorage.getItem('role');
  if (role === 'FARMER') return true;

  router.navigate(['/dashboard']); // redirect to login page
  return false;
};
