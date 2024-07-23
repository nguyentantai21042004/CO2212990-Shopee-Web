import { Inject, inject, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { RegisterDTO } from '../dtos/register.dtos';
import { ApiResponse } from '../responses/api.response';
import { LoginDTO } from '../dtos/login.dtos';
import { UserResponse } from '../responses/user.response';
import { DOCUMENT } from '@angular/common';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiRegister = `${environment.apiBaseUrl}/users/register`;
    private apiLogin = `${environment.apiBaseUrl}/users/login`;

    private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

    localStorage?: Storage;
    constructor(@Inject(DOCUMENT) private document: Document) {
        this.localStorage = document.defaultView?.localStorage;
    }

    private http = inject(HttpClient);
    private httpUtilService = inject(HttpUtilService);

    private apiConfig = {
        headers: this.httpUtilService.createHeaders(),
    }

    saveUserResponseToLocalStorage(userResponse?: UserResponse) {
        try {
            if (userResponse == null || !userResponse)
                return;

            // Convert the userResponse object to a JSON string
            const userResponseJson = JSON.stringify(userResponse);

            this.localStorage?.setItem('user', userResponseJson);
            console.log('User response saved to local storage.');
        }
        catch (error) {
            console.log('Error saving user response to local storage: ', error);
        }
    }

    register(registerDTO: RegisterDTO): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiRegister, registerDTO, this.apiConfig);
    }

    login(loginDTO: LoginDTO): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiLogin, loginDTO, this.apiConfig);
    }

    getUserDetail(token: string): Observable<ApiResponse> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        });

        return this.http.post<ApiResponse>(this.apiUserDetail, {}, { headers });
    }
}