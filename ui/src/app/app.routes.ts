import { Routes } from '@angular/router';
import { AccesForm } from './components/acces-form/acces-form';
import { SimulationForm } from './components/simulation-form/simulation-form';

export const routes: Routes = [
  { path: '', component: AccesForm },
  { path: 'simulation', component: SimulationForm },
];