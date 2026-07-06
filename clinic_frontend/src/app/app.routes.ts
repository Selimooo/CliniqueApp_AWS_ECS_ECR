import { Routes } from '@angular/router';
import { PatientListComponent } from './features/patient/patient-list/patient-list.component';
import { PatientFormComponent } from './features/patient/patient-form/patient-form.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'patients',
    pathMatch: 'full',
  },
  {
    path: 'patients',
    component: PatientListComponent,
  },
  {
    path: 'patients/add',
    component: PatientFormComponent,
  },
];
