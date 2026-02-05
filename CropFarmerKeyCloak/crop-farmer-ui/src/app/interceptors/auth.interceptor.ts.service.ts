import { HttpInterceptorFn } from '@angular/common/http';
import { keycloak } from '../KeyClock/keycloak.config.ts.service';
import { AuthService } from '../service/services/auth.service';
import { inject } from '@angular/core';


export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};

