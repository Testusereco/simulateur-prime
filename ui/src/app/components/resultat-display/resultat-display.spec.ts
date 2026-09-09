import { TestBed } from '@angular/core/testing';
import { ResultatDisplay } from './resultat-display';
import { SimulationResult } from '../../services/simulation';

describe('ResultatDisplay', () => {
  function creerComposant(resultat: SimulationResult) {
    const fixture = TestBed.createComponent(ResultatDisplay);
    fixture.componentRef.setInput('resultat', resultat);
    fixture.detectChanges();
    return fixture;
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ResultatDisplay],
    });
  });

  it('calcule le libellé "éligible" et le montant formaté', () => {
    const fixture = creerComposant({ statut: 'ELIGIBLE', montant: 1000, motif: null });
    const component = fixture.componentInstance;

    expect(component.statutAffiche()).toBe('éligible');
    expect(component.montantAffiche()).toBe('1000 €');
  });

  it('calcule le libellé "inéligible" sans montant', () => {
    const fixture = creerComposant({ statut: 'INELIGIBLE', montant: null, motif: 'A < 1000' });
    const component = fixture.componentInstance;

    expect(component.statutAffiche()).toBe('inéligible');
    expect(component.montantAffiche()).toBeNull();
  });

  it('calcule le libellé "erreur" sans montant', () => {
    const fixture = creerComposant({ statut: 'ERREUR', montant: null, motif: 'H doit être > 0' });
    const component = fixture.componentInstance;

    expect(component.statutAffiche()).toBe('erreur');
    expect(component.montantAffiche()).toBeNull();
  });
});