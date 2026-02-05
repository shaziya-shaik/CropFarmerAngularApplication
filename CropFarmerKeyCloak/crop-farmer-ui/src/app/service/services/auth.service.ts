import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import Keycloak from 'keycloak-js';


@Injectable({ providedIn: 'root' })
export class AuthService {
  private keycloak!: Keycloak;

  username$ = new BehaviorSubject<string | null>(null);
  email$ = new BehaviorSubject<string | null>(null);
  role$ = new BehaviorSubject<string | null>(null);

  async initKeycloak(): Promise<void> {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8087',
      realm: 'crop-app',
      clientId: 'angular-client'
    });

    await this.keycloak.init({ onLoad: 'login-required', checkLoginIframe: false });

    // Username (UI only)
    const username = this.keycloak.tokenParsed?.['preferred_username'] || null;
    if (username) this.username$.next(username);

    // Email (BACKEND)
    const email = this.keycloak.tokenParsed?.['email'] || null;
    if (email) this.email$.next(email);

    // Roles
    const realmRoles = this.keycloak.tokenParsed?.realm_access?.roles || [];
    const clientRoles = this.keycloak.tokenParsed?.resource_access?.['angular-client']?.roles || [];

    if (realmRoles.includes('FARMER') || clientRoles.includes('FARMER')) {
      this.role$.next('FARMER');
    } else if (realmRoles.includes('USER') || clientRoles.includes('USER')) {
      this.role$.next('USER');
    }

    this.role$.subscribe(role => {
      if (role) localStorage.setItem('role', role);
    });

    setInterval(() => {
      this.keycloak.updateToken(30).catch(() => this.logout());
    }, 10000);
  }

  logout() {
    this.keycloak.logout();
    this.username$.next(null);
    this.email$.next(null);
    this.role$.next(null);
    localStorage.clear();
  }

  getToken() {
    return this.keycloak.token;
  }
}