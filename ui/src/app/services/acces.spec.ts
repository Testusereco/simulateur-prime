import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { Acces, AccesResponse } from './acces';

describe('Acces', () => {
  let service: Acces;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(Acces);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("envoie une requête POST vers /api/acces avec l'âge", () => {
    const reponseAttendue: AccesResponse = {
      autorise: false,
      message: 'Citoyen inéligible : âge hors limites',
    };

    service.verifierAcces(17).subscribe((reponse: AccesResponse) => {
      expect(reponse).toEqual(reponseAttendue);
    });

    const req = httpMock.expectOne('/api/acces');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ age: 17 });
    req.flush(reponseAttendue);
  });
});