import { TestBed, ComponentFixture } from '@angular/core/testing';
import { of } from 'rxjs';
import { AccesForm } from './acces-form';
import { Acces, AccesResponse } from '../../services/acces';
import { Router } from '@angular/router';

describe('AccesForm', () => {
  let component: AccesForm;
  let fixture: ComponentFixture<AccesForm>;
  let verifierAccesMock: ReturnType<typeof vi.fn>;
  let navigateMock: ReturnType<typeof vi.fn>;

    beforeEach(() => {
    verifierAccesMock = vi.fn();
    navigateMock = vi.fn();

    TestBed.configureTestingModule({
      imports: [AccesForm],
      providers: [
        { provide: Acces, useValue: { verifierAcces: verifierAccesMock } },
        { provide: Router, useValue: { navigateByUrl: navigateMock } },
      ],
    });

    fixture = TestBed.createComponent(AccesForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
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

  it("affiche un champ de saisie de l'âge et un bouton de validation", () => {
    const input = fixture.nativeElement.querySelector('[data-testid="age-input"]');
    const button = fixture.nativeElement.querySelector('[data-testid="submit-button"]');

    expect(input).not.toBeNull();
    expect(button).not.toBeNull();
  });

  it("saisir un âge puis cliquer sur valider déclenche l'appel au service", () => {
    verifierAccesMock.mockReturnValue(of({ autorise: true, message: null }));

    const input: HTMLInputElement = fixture.nativeElement.querySelector('[data-testid="age-input"]');
    input.value = '40';
    input.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    const button: HTMLButtonElement = fixture.nativeElement.querySelector('[data-testid="submit-button"]');
    button.click();
    fixture.detectChanges();

    expect(verifierAccesMock).toHaveBeenCalledWith(40);
  });

  it("affiche le message d'erreur à l'écran quand l'accès est refusé", () => {
    verifierAccesMock.mockReturnValue(
      of({ autorise: false, message: 'Citoyen inéligible : âge hors limites' }),
    );

    component.age.set(17);
    component.onSubmit();
    fixture.detectChanges();

    const messageEl = fixture.nativeElement.querySelector('[data-testid="error-message"]');
    expect(messageEl?.textContent).toContain('Citoyen inéligible : âge hors limites');
  });

  it("n'affiche aucun message d'erreur quand l'accès est autorisé", () => {
    verifierAccesMock.mockReturnValue(of({ autorise: true, message: null }));

    component.age.set(25);
    component.onSubmit();
    fixture.detectChanges();

    const messageEl = fixture.nativeElement.querySelector('[data-testid="error-message"]');
    expect(messageEl).toBeNull();
  });
    it('navigue vers /simulation quand l\'accès est autorisé', () => {
        
    const reponse: AccesResponse = { autorise: true, message: null };
    verifierAccesMock.mockReturnValue(of(reponse));

    component.age.set(25);
    component.onSubmit();

    expect(navigateMock).toHaveBeenCalledWith('/simulation');
  });

  it("ne navigue pas quand l'accès est refusé", () => {
       
    const reponse: AccesResponse = { autorise: false, message: 'Citoyen inéligible : âge hors limites' };
    verifierAccesMock.mockReturnValue(of(reponse));

    component.age.set(17);
    component.onSubmit();

    expect(navigateMock).not.toHaveBeenCalled();
  });
});