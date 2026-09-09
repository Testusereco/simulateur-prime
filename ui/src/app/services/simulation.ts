import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SimulationRequest {
  age: number;
  a: number;
  b: number;
  c: number;
  d: number;
  e: number;
  f: number;
  g: number;
  h: number;
}

export type Statut = 'ELIGIBLE' | 'INELIGIBLE' | 'ERREUR';

export interface SimulationResult {
  statut: Statut;
  montant: number | null;
  motif: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class Simulation {
  private readonly http = inject(HttpClient);

  simuler(requete: SimulationRequest): Observable<SimulationResult> {
    return this.http.post<SimulationResult>('/api/simulation', requete);
  }
}