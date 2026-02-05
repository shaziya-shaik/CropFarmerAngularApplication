import Keycloak from 'keycloak-js';

export const keycloak = new Keycloak({
  url: 'http://localhost:8087',
  realm: 'crop-app',
  clientId: 'angular-client'
});

export function initializeKeycloak() {
  return () =>
    keycloak.init({
      onLoad: 'login-required',
      checkLoginIframe: false
    });
}
