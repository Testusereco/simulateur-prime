import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AccesResponse {
  autorise: boolean;
  message: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class Acces {
  private readonly http = inject(HttpClient);

  verifierAcces(age: number): Observable<AccesResponse> {
    return this.http.post<AccesResponse>('/api/acces', { age });
  }
}