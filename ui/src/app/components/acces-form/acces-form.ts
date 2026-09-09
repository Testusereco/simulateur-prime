import { Component, inject, signal } from '@angular/core';
import { Acces, AccesResponse } from '../../services/acces';
import { Router } from '@angular/router';

@Component({
  selector: 'app-acces-form',
  imports: [],
  templateUrl: './acces-form.html',
  styleUrl: './acces-form.scss',
})
export class AccesForm {
  private readonly accesService = inject(Acces);
  private readonly router = inject(Router);

  age = signal<number>(0);
  resultat = signal<AccesResponse | null>(null);

 onSubmit(): void {
    this.accesService.verifierAcces(this.age()).subscribe((reponse) => {
      this.resultat.set(reponse);
      if (reponse.autorise) {
        this.router.navigateByUrl('/simulation');
      }
    });
  }
}