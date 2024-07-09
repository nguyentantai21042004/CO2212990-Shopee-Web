import { Component } from '@angular/core';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent {
  currentIndex = 0;
  slideWidth: number = 120;
  moveX: number = 0;
  slides = [0, 1, 2, 3, 4, 5, 4, 4, 4, 4, 4];
  slidesToShow = 10;
  slidesToMove = 10;

  ngOnInit(): void {
    this.updateSlideWidth();
  }

  updateSlideWidth() {
    this.slideWidth = 120;
  }
  prev() {
    if (this.currentIndex > 0) {
      this.currentIndex = Math.max(0, this.currentIndex - this.slidesToMove);
    }
  }

  next() {
    if (this.currentIndex < this.slides.length - this.slidesToShow) {
      this.currentIndex = Math.min(this.slides.length - this.slidesToShow, this.currentIndex + this.slidesToMove);
    }
  }

  getTransform() {
    return `translateX(-${this.currentIndex * this.slideWidth + this.moveX}px)`;
  }
}
