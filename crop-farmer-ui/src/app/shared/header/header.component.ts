import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AsyncPipe } from '@angular/common';
import { AuthService } from '../../service/services/auth.service';
import { Observable } from 'rxjs/internal/Observable';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, AsyncPipe],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
   providers: [AuthService]
})


export class HeaderComponent implements OnInit {

  
  role$!: Observable<string | null>;
  username$!: Observable<string | null>;

 constructor(private auth: AuthService, private router: Router) {}


  ngOnInit() {
    this.role$ = this.auth.role$;
    this.username$ = this.auth.username$;
  }

  logout() {
    this.auth.logout();
     this.router.navigate(['/login']);
  }
}