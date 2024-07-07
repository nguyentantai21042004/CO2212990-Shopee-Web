import { inject, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { RegisterDTO } from '../dtos/register.dtos';
import { ApiResponse } from '../responses/api.response';
import { LoginDTO } from '../dtos/login.dtos';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiRegister = `${environment.apiBaseUrl}/users/register`;
    private apiLogin = `${environment.apiBaseUrl}/users/login`;

    private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

    private http = inject(HttpClient);
    private httpUtilService = inject(HttpUtilService);

    private apiConfig = {
        headers: this.httpUtilService.createHeaders(),
    }

    register(registerDTO: RegisterDTO): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiRegister, registerDTO, this.apiConfig);
    }
    login(loginDTO: LoginDTO): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiLogin, loginDTO, this.apiConfig);
    }
    getUserDetail(token: string): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiUserDetail,
            {
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                })
            }
        )
    }
}