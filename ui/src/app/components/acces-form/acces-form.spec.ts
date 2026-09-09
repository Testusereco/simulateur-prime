import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AccesForm } from './acces-form';
import { Acces, AccesResponse } from '../../services/acces';

describe('AccesForm', () => {
  let component: AccesForm;
  let verifierAccesMock: ReturnType<typeof vi.fn>;

  beforeEach(() => {
    verifierAccesMock = vi.fn();

    TestBed.configureTestingModule({
      imports: [AccesForm],
      providers: [{ provide: Acces, useValue: { verifierAcces: verifierAccesMock } }],
    });

    const fixture = TestBed.createComponent(AccesForm);
    component = fixture.componentInstance;
  });

  it("appelle Acces.verifierAcces avec l'âge saisi", () => {
    const reponse: AccesResponse = { autorise: true, message: null };
    verifierAccesMock.mockReturnValue(of(reponse));

    component.age.set(25);
    component.onSubmit();

    expect(verifierAccesMock).toHaveBeenCalledWith(25);
    expect(component.resultat()).toEqual(reponse);
  });

  it('affiche le message de refus quand le citoyen est inéligible', () => {
    const reponse: AccesResponse = {
      autorise: false,
      message: 'Citoyen inéligible : âge hors limites',
    };
    verifierAccesMock.mockReturnValue(of(reponse));

    component.age.set(17);
    component.onSubmit();

    expect(component.resultat()?.autorise).toBe(false);
    expect(component.resultat()?.message).toBe('Citoyen inéligible : âge hors limites');
  });
});