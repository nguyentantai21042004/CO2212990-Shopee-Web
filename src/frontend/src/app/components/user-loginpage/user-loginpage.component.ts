import { HttpErrorResponse } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dtos/login.dtos';
import { ApiResponse } from 'src/app/responses/api.response';
import { UserResponse } from 'src/app/responses/user.response';
import { TokenService } from 'src/app/services/token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-loginpage',
  templateUrl: './user-loginpage.component.html',
  styleUrls: ['./user-loginpage.component.scss']
})
export class UserLoginpageComponent {
  @ViewChild('loginForm') loginForm!: NgForm;

  isLoginDTO: boolean | null = null;
  isUserName: boolean | null = null;

  userName: string = '';
  phoneNumber: string = '';
  password: string = '';
  email: string = '';
  userResponse?: UserResponse;

  loginDTO: LoginDTO = {
    phoneNumber: '',
    email: '',
    password: '',
    roleName: 'USER'
  }

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService) { }

  isValidPhoneNumber(phone: string): boolean {
    const phoneRegex = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
    return phoneRegex.test(phone);
  }

  isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  onUserNameChange(): void {
    if (this.isValidEmail(this.userName)) {
      this.email = this.userName;
      this.loginDTO.email = this.email;
      this.isUserName = true;

      this.isLoginDTO = this.isUserName && !!this.password.trim();

    } else if (this.isValidPhoneNumber(this.userName)) {
      this.phoneNumber = this.userName;
      this.loginDTO.phoneNumber = this.phoneNumber;
      this.isUserName = true;

      this.isLoginDTO = this.isUserName && !!this.password.trim();
    } else {
      this.isUserName = false;
      this.isLoginDTO = false;
    }
  }

  onPasswordChange(): void {
    this.loginDTO.password = this.password;
    this.isLoginDTO = this.isUserName && !!this.password.trim();
  }

  login(): void {
    if (!this.isLoginDTO)
      return;

    this.userService.login(this.loginDTO).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log('Login successful:', apiResponse);
        const token = apiResponse?.data.token ? apiResponse?.data.token : '';

        this.tokenService.setToken(token);
        this.getUserDetail();
      },
      complete: () => {
        this.router.navigate(['/user/account/profile']);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Login error:', error);
      }
    });
  }


  getUserDetail() {
    const token = this.tokenService.getToken();

    this.userService.getUserDetail(token).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log('User detail fetched successfully:', apiResponse);

        this.userResponse = {
          ...apiResponse.data,
        };
        this.userService.saveUserResponseToLocalStorage(this.userResponse);

        console.log('Token set:', token);
      },
      complete: () => {
        console.log('Get user detail request completed');
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error fetching user detail:', error);
      }
    });
  }

  arrayToDate(dateArray: number[]): Date {
    return new Date(
      dateArray[0],
      dateArray[1] - 1,
      dateArray[2],
      dateArray[3],
      dateArray[4],
      dateArray[5],
      Math.floor(dateArray[6] / 1e6)
    );
  }
}
