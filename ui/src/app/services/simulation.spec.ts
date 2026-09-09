import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { Simulation, SimulationRequest, SimulationResult } from './simulation';

describe('Simulation', () => {
  let service: Simulation;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(Simulation);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('envoie une requête POST vers /api/simulation avec les 9 valeurs', () => {
    const requete: SimulationRequest = {
      age: 25,
      a: 1000,
      b: 1000,
      c: 0,
      d: 0,
      e: 1,
      f: 1,
      g: 1,
      h: 1,
    };
    const reponseAttendue: SimulationResult = {
      statut: 'ELIGIBLE',
      montant: 2000,
      motif: null,
    };

    service.simuler(requete).subscribe((reponse: SimulationResult) => {
      expect(reponse).toEqual(reponseAttendue);
    });

    const req = httpMock.expectOne('/api/simulation');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(requete);
    req.flush(reponseAttendue);
  });
});