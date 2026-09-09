import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Simulation, SimulationRequest, SimulationResult } from '../../services/simulation';

@Component({
  selector: 'app-simulation-form',
  imports: [ReactiveFormsModule],
  templateUrl: './simulation-form.html',
  styleUrl: './simulation-form.scss',
})
export class SimulationForm {
  private readonly simulationService = inject(Simulation);
  private readonly fb = inject(FormBuilder);

  resultat = signal<SimulationResult | null>(null);

  form = this.fb.group({
    age: [0, Validators.required],
    a: [0, Validators.required],
    b: [0, Validators.required],
    c: [0, Validators.required],
    d: [0, Validators.required],
    e: [0, Validators.required],
    f: [0, Validators.required],
    g: [0, Validators.required],
    h: [0, Validators.required],
  });

  onSubmit(): void {
    const requete = this.form.getRawValue() as SimulationRequest;
    this.simulationService.simuler(requete).subscribe((reponse) => {
      this.resultat.set(reponse);
    });
  }
}