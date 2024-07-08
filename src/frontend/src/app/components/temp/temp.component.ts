import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-temp',
  templateUrl: './temp.component.html',
  styleUrls: ['./temp.component.scss']
})
export class TempComponent {
  items = [1, 2, 3, 4, 5, 6, 7, 8];
  currentIndex = 0;
  startX: number = 0;
  moveX: number = 0;
  dragging: boolean = false;
  slideWidth: number = window.innerWidth;

  ngOnInit() {
    this.updateSlideWidth();
  }

  @HostListener('window:resize')
  onWindowResize() {
    this.updateSlideWidth();
  }

  updateSlideWidth() {
    this.slideWidth = window.innerWidth;
  }

  prev() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }
  }

  next() {
    if (this.currentIndex < this.items.length - 1) {
      this.currentIndex++;
    }
  }

  getTransform() {
    return `translateX(-${this.currentIndex * this.slideWidth + this.moveX}px)`;
  }

  goToSlide(index: number) {
    this.currentIndex = index;
  }

  onMouseDown(event: MouseEvent) {
    this.startX = event.clientX;
    this.dragging = true;
  }

  onMouseMove(event: MouseEvent) {
    if (this.dragging) {
      this.moveX = event.clientX - this.startX;
    }
  }

  onMouseUp() {
    this.dragging = false;
    if (Math.abs(this.moveX) > this.slideWidth / 4) {
      if (this.moveX > 0 && this.currentIndex > 0) {
        this.currentIndex--;
      } else if (this.moveX < 0 && this.currentIndex < this.items.length - 1) {
        this.currentIndex++;
      }
    }
    this.moveX = 0;
  }

  @HostListener('window:mouseup')
  onWindowMouseUp() {
    if (this.dragging) {
      this.onMouseUp();
    }
  }
}
