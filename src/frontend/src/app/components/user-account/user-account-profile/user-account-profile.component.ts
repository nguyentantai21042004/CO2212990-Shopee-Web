import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/responses/user.response';
import { TokenService } from 'src/app/services/token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-account-profile',
  templateUrl: './user-account-profile.component.html',
  styleUrls: ['./user-account-profile.component.scss']
})
export class UserAccountProfileComponent implements OnInit {
  userResponse?: UserResponse;
  token: string = '';

  constructor(private router: Router,
    private userService: UserService,
    private tokenService: TokenService) {

  }

  ngOnInit(): void {
    debugger;
    this.token = this.tokenService.getToken();
    debugger;
    this.userService.getUserDetail(this.token).subscribe({

      next: (response: any) => {
        this.userResponse = {
          ...response
        };
      },

      complete: () => {
        debugger;
      },

      error: (error: HttpErrorResponse) => {
        debugger;
        console.error(error?.error?.message ?? '');
      }

    })
  }
}
