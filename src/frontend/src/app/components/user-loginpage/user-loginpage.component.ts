import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dtos/login.dtos';
import { ApiResponse } from 'src/app/responses/api.response';
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

  loginDTO: LoginDTO = {
    phoneNumber: '',
    email: '',
    password: '',
    roleName: 'USER'
  }

  constructor(private router: Router, private userService: UserService) { }

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
        debugger
        const { token } = apiResponse.data.token;

        this.userService.getUserDetail(token).subscribe({
          next: (apiResponse2: ApiResponse) => {
            debugger
            
          }
        })

      }
    })
  }
}
