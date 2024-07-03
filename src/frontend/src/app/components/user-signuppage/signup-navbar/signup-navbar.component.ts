import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup-navbar',
  templateUrl: './signup-navbar.component.html',
  styleUrls: ['./signup-navbar.component.scss']
})
export class SignupNavbarComponent {
  constructor(private router: Router) { }

  navigateToHomepage() {
    this.router.navigate(['']);
  }
}
