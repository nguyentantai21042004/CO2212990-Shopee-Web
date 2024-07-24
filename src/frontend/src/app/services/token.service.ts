import { DOCUMENT } from "@angular/common";
import { Inject, Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
    providedIn: 'root',
})
export class TokenService {
    private readonly TOKEN_KEY = 'access_token';
    private jwtService = new JwtHelperService();

    localStorage?: Storage;

    constructor(@Inject(DOCUMENT) private document: Document) {
        this.localStorage = document.defaultView?.localStorage;
    }

    getUserId(): string {
        let token = this.getToken();
        if (!token) {
            return '';
        }

        let userObject = this.jwtService.decodeToken(token);
        return 'userId' in userObject ? userObject['userId'] : '';
    }

    getToken(): string {
        return this.localStorage?.getItem(this.TOKEN_KEY) ?? '';
    }

    setToken(token: string): void {
        this.localStorage?.setItem(this.TOKEN_KEY, token);
    }

    removeToken(): void {
        this.localStorage?.removeItem(this.TOKEN_KEY);
    }

    isTokenExpired(): boolean {
        let token = this.getToken();
        if (!token) {
            return true;
        }

        return this.jwtService.isTokenExpired(token);
    }
}
