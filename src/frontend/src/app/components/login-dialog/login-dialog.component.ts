import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

declare var Swiper: any; // Declare Swiper globally if not already done

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<LoginDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router
  ) { }

  navigateToNextSlide(): void {
    const swiperContainer = document.querySelector('.swiper-container') as HTMLElement;
    if (swiperContainer) {
      const swiperInstance = new Swiper(swiperContainer, {
        // Your Swiper configuration options here
      });
      swiperInstance.slideNext();
      this.dialogRef.close();
    } else {
      console.error('Swiper container not found.');
    }
  }
}
