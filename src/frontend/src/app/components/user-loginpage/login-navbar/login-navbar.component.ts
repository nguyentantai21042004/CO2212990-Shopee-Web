import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-navbar',
  templateUrl: './login-navbar.component.html',
  styleUrls: ['./login-navbar.component.scss']
})
export class LoginNavbarComponent {
  constructor(private router: Router) { }

  navigateToHomepage() {
    this.router.navigate(['']);
  }
}
