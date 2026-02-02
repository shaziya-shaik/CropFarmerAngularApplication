import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private API_URL = 'http://localhost:8080/login';

  private roleSubject = new BehaviorSubject<string | null>(
    localStorage.getItem('role')
  );
  private usernameSubject = new BehaviorSubject<string | null>(
    localStorage.getItem('username')
  );

  role$ = this.roleSubject.asObservable();
  username$ = this.usernameSubject.asObservable();

  constructor(private http: HttpClient) {}

  // 🔐 CALL SPRING BOOT LOGIN / REGISTER API
  loginApi(payload: {
    emailId: string;
    password: string;
    role: string;
  }) {
    return this.http.post(this.API_URL + '/', payload, {
      responseType: 'text'   // because backend returns String
    });
  }

  // 💾 SAVE AUTH DATA AFTER SUCCESS
  setAuth(username: string, role: string) {
    localStorage.setItem('username', username);
    localStorage.setItem('role', role);
    this.usernameSubject.next(username);
    this.roleSubject.next(role);
  }

  logout() {
    localStorage.clear();
    this.usernameSubject.next(null);
    this.roleSubject.next(null);
  }

  getRole() {
    return this.roleSubject.value;
  }

  getUsername() {
    return this.usernameSubject.value;
  }
}
