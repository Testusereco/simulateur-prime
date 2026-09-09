import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { SimulationForm } from './simulation-form';
import { Simulation, SimulationRequest, SimulationResult } from '../../services/simulation';

describe('SimulationForm', () => {
  let component: SimulationForm;
  let simulerMock: ReturnType<typeof vi.fn>;

  beforeEach(() => {
    simulerMock = vi.fn();

    TestBed.configureTestingModule({
      imports: [SimulationForm],
      providers: [{ provide: Simulation, useValue: { simuler: simulerMock } }],
    });

    const fixture = TestBed.createComponent(SimulationForm);
    component = fixture.componentInstance;
  });

  it('construit un formulaire avec les 9 champs attendus', () => {
    const champsAttendus = ['age', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    champsAttendus.forEach((champ) => {
      expect(component.form.contains(champ)).toBe(true);
    });
  });

  it('appelle Simulation.simuler avec les valeurs du formulaire', () => {
    const reponse: SimulationResult = { statut: 'ELIGIBLE', montant: 2000, motif: null };
    simulerMock.mockReturnValue(of(reponse));

    component.form.setValue({
      age: 25,
      a: 1000,
      b: 1000,
      c: 0,
      d: 0,
      e: 1,
      f: 1,
      g: 1,
      h: 1,
    });
    component.onSubmit();

    const requeteAttendue: SimulationRequest = {
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
    expect(simulerMock).toHaveBeenCalledWith(requeteAttendue);
    expect(component.resultat()).toEqual(reponse);
  });
    it('affiche les 9 champs de saisie et un bouton de validation', () => {
    const fixture = TestBed.createComponent(SimulationForm);
    fixture.detectChanges();

    const champs = ['age', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    champs.forEach((champ) => {
      const input = fixture.nativeElement.querySelector(`[data-testid="${champ}-input"]`);
      expect(input).not.toBeNull();
    });

    const button = fixture.nativeElement.querySelector('[data-testid="submit-button"]');
    expect(button).not.toBeNull();
  });

  it('affiche le montant quand le résultat est éligible', () => {
    const reponse: SimulationResult = { statut: 'ELIGIBLE', montant: 2000, motif: null };
    simulerMock.mockReturnValue(of(reponse));

    const fixture = TestBed.createComponent(SimulationForm);
    const comp = fixture.componentInstance;
    fixture.detectChanges();

    comp.form.setValue({ age: 25, a: 1000, b: 1000, c: 0, d: 0, e: 1, f: 1, g: 1, h: 1 });
    comp.onSubmit();
    fixture.detectChanges();

    const montantEl = fixture.nativeElement.querySelector('[data-testid="montant"]');
    expect(montantEl?.textContent).toContain('2000');
  });

  it('affiche le motif quand le résultat est inéligible', () => {
    const reponse: SimulationResult = {
      statut: 'INELIGIBLE',
      montant: null,
      motif: 'inéligible (A < 1000)',
    };
    simulerMock.mockReturnValue(of(reponse));

    const fixture = TestBed.createComponent(SimulationForm);
    const comp = fixture.componentInstance;
    fixture.detectChanges();

    comp.form.setValue({ age: 25, a: 500, b: 1000, c: 0, d: 0, e: 1, f: 1, g: 1, h: 1 });
    comp.onSubmit();
    fixture.detectChanges();

    const motifEl = fixture.nativeElement.querySelector('[data-testid="motif"]');
    expect(motifEl?.textContent).toContain('inéligible (A < 1000)');
  });
});