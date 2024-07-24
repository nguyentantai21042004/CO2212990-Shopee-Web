import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDetail } from 'src/app/responses/user.detail';
import { UserResponse } from 'src/app/responses/user.response';
import { TokenService } from 'src/app/services/token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-account-profile',
  templateUrl: './user-account-profile.component.html',
  styleUrls: ['./user-account-profile.component.scss']
})
export class UserAccountProfileComponent implements OnInit {
  userDetail?: UserDetail;
  token: string = '';

  constructor(private router: Router,
    private userService: UserService,
    private tokenService: TokenService) { }

  ngOnInit(): void {
    this.token = this.tokenService.getToken();
    debugger;
    console.log('Retrieved token:', this.token);

    if (this.token) {
      this.userService.getUserDetail(this.token).subscribe({
        next: (response: any) => {
          console.log('User detail response:', response);
          this.userDetail = response.data;
          console.log('User detail:', this.userDetail);
        },
        complete: () => {
          console.log('Get user detail request completed');
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error fetching user detail:', error);
          if (error.status === 401) {
            this.router.navigate(['/login']);
          }
        }
      });
    } else {
      console.warn('No token found, redirecting to login');
      this.router.navigate(['/login']);
    }
  }
}
