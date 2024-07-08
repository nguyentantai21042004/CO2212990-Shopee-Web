import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss']
})
export class CarouselComponent implements OnInit {
  currentIndex = 0;
  slides = [
    '../../../../assets/carousel/slides/slide01.jpg',
    '../../../../assets/carousel/slides/slide02.jpg',
    '../../../../assets/carousel/slides/slide03.jpg',
    '../../../../assets/carousel/slides/slide04.jpg',
    '../../../../assets/carousel/slides/slide05.jpg',
    '../../../../assets/carousel/slides/slide06.jpg',
    '../../../../assets/carousel/slides/slide07.jpg',
    '../../../../assets/carousel/slides/slide08.jpg',
    '../../../../assets/carousel/slides/slide09.jpg',
    '../../../../assets/carousel/slides/slide10.jpg',
  ];
  slideWidth: number = 800;
  autoplayInterval: any;

  ngOnInit(): void {
    this.updateSlideWidth();
    this.startAutoplay();
  }

  ngOnDestroy(): void {
    this.stopAutoplay();
  }

  @HostListener('window:resize')
  onWindowResize() {
    this.updateSlideWidth();
  }

  updateSlideWidth() {
    this.slideWidth = 800;
  }

  startAutoplay() {
    this.autoplayInterval = setInterval(() => {
      this.next();
    }, 6000); // Change slide every 7 seconds
  }

  stopAutoplay() {
    clearInterval(this.autoplayInterval);
  }

  prev() {
    if (this.currentIndex === 0) {
      this.currentIndex = this.slides.length - 1;
    } else {
      this.currentIndex--;
    }
  }

  next() {
    if (this.currentIndex === this.slides.length - 1) {
      this.currentIndex = 0;
    } else {
      this.currentIndex++;
    }
  }

  getTransform() {
    return `translateX(-${this.currentIndex * this.slideWidth}px)`;
  }

  goToSlide(index: number) {
    this.currentIndex = index;
  }
}
