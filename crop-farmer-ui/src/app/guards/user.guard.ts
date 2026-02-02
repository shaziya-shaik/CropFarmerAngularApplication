import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const userGuard: CanActivateFn = () => {
  const router = inject(Router);
  const role = localStorage.getItem('role');

  if (role === 'USER') {
    return true;
  }

  // Redirect others to dashboard or login
  router.navigate(['/dashboard']);
  return false;
};
