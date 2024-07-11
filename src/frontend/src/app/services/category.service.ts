import { inject, Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { Observable } from "rxjs";
import { ApiResponse } from "../responses/api.response";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    private http = inject(HttpClient);
    private apiBaseUrl = environment.apiBaseUrl;

    getCategory():Observable<ApiResponse> {
        return this.http.get<ApiResponse>(`${environment.apiBaseUrl}/categories`);
    }
}