import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/responses/api.response';
import { CategoryResponse } from 'src/app/responses/category.response';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent {
  currentIndex = 0;
  slideWidth: number = 120;
  moveX: number = 0;
  slides: number = 0;
  slidesToShow = 10;
  slidesToMove = 10;

  categories: CategoryResponse[] | undefined;
  groupedCategories: CategoryResponse[][] = [];

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.updateSlideWidth();
    this.getAllCategories();
  }

  groupCategories(categories: CategoryResponse[]): CategoryResponse[][] {
    const grouped: CategoryResponse[][] = [];
    for (let i = 0; i < categories.length; i += 2) {
      grouped.push(categories.slice(i, i + 2));
    }
    return grouped;
  }

  getAllCategories() {
    this.categoryService.getCategory().subscribe({
      next: (apiResponse: ApiResponse) => {
        debugger;
        this.categories = apiResponse.data;

        this.categories?.forEach((category) => {
          category.imageUrl = `${environment.apiBaseUrl}/categories/viewImage/${category.imageUrl}`;
        });

        this.groupedCategories = this.groupCategories(this.categories ?? []);

        this.slides = this.categories ? Math.ceil(this.categories.length / 2) : 0;
      },
      complete: () => {
        console.log(this.slides);
        console.log(this.categories);
        debugger;
      },
      error: (error: HttpErrorResponse) => {
        debugger;
        console.error(error?.error?.message ?? '');
      }
    });
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
    if (this.currentIndex < this.slides - this.slidesToShow) {
      this.currentIndex = Math.min(this.slides - this.slidesToShow, this.currentIndex + this.slidesToMove);
    }
  }

  getTransform() {
    return `translateX(-${this.currentIndex * this.slideWidth + this.moveX}px)`;
  }
}
