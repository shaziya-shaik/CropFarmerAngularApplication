import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../service/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  role$;
  username$;

  constructor(private authService: AuthService) {
    this.role$ = this.authService.role$;
    this.username$ = this.authService.username$;
  }

  logout() {
    this.authService.logout();
  }
}
