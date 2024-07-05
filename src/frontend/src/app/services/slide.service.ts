// slide.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SlideService {
    private slideChangeSubject = new Subject<number>();

    slideChange$ = this.slideChangeSubject.asObservable();

    changeSlide(index: number) {
        this.slideChangeSubject.next(index);
    }
}
