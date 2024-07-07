import { inject, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { RegisterDTO } from '../dtos/register.dtos';
import { ApiResponse } from '../responses/api.response';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiRegister = `${environment.apiBaseUrl}/users/register`;

    private http = inject(HttpClient);
    private httpUtilService = inject(HttpUtilService);

    private apiConfig = {
        headers: this.httpUtilService.createHeaders(),
    }   

    register(registerDTO: RegisterDTO): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiRegister, registerDTO, this.apiConfig);
    }
}