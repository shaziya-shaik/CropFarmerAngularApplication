import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email = '';
  password = '';
  role = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login(event: Event) {
    event.preventDefault();

    const payload = {
      emailId: this.email,
      password: this.password,
      role: this.role
    };

    this.auth.loginApi(payload).subscribe({
      next: (response) => {
        console.log(response); // backend message

        
        this.auth.setAuth(this.email, this.role);

        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        alert(err.error); // shows WRONG_PASSWORD, etc.
      }
    });
  }
}
