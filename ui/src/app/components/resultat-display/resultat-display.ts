import { Component, computed, input } from '@angular/core';
import { SimulationResult } from '../../services/simulation';

@Component({
  selector: 'app-resultat-display',
  imports: [],
  templateUrl: './resultat-display.html',
  styleUrl: './resultat-display.scss',
})
export class ResultatDisplay {
  resultat = input.required<SimulationResult>();

  statutAffiche = computed(() => {
    switch (this.resultat().statut) {
      case 'ELIGIBLE':
        return 'éligible';
      case 'INELIGIBLE':
        return 'inéligible';
      case 'ERREUR':
        return 'erreur';
    }
  });

  montantAffiche = computed(() => {
    const montant = this.resultat().montant;
    return montant === null ? null : `${montant} €`;
  });
}