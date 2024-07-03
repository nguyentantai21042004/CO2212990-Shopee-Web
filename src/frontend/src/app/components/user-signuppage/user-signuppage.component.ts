import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { RegisterDTO } from 'src/app/dtos/register.dtos';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';

@Component({
  selector: 'app-user-signuppage',
  templateUrl: './user-signuppage.component.html',
  styleUrls: ['./user-signuppage.component.scss']
})
export class UserSignuppageComponent {
  @ViewChild('registerForm') registerForm!: NgForm;

  phoneNumber: string;
  email: string;
  isPhoneNumberValid: boolean | null = null;
  isEmailValid: boolean | null = null;

  registerDTO: RegisterDTO = {
    phoneNumber: '',
    email: '',
    password: '',
    retypePassword: '',
    roleName: '',
    facebookAccountId: '',
    googleAccountId: ''
  }

  constructor(private router: Router, private dialog: MatDialog) {
    this.phoneNumber = '';
    this.email = '';
    this.registerDTO.roleName = "USER";
    this.isPhoneNumberValid = null;
  }

  isValidPhoneNumber(phone: string): boolean {
    const phoneRegex = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
    return phoneRegex.test(phone);
  }

  onPhoneNumberChange() {
    if (this.phoneNumber.trim() === '') {
      this.isPhoneNumberValid = null;
    } else {
      this.isPhoneNumberValid = this.isValidPhoneNumber(this.phoneNumber);
    }
  }

  openDialog() {
    this.registerDTO.phoneNumber = this.phoneNumber;

    const dialogRef = this.dialog.open(LoginDialogComponent, {
      disableClose: true,
      data: { userRegister: this.phoneNumber }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  goToNextSlide(swiperContainer: any): void {
    swiperContainer.swiper.slideNext();
  }


  goToPrevSlide(swiperContainer: any): void {
    swiperContainer.swiper.slidePrev();
  }

  onEmailChange() {

  }
}
