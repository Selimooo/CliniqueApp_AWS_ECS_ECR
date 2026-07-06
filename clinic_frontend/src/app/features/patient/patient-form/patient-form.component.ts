import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PatientService } from '../../../services/patient/patient.service';
import { Patient } from '../../../models/patient';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './patient-form.component.html',
  styleUrl: './patient-form.component.css',
})
export class PatientFormComponent {
   patient: Patient = {
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
  };

  constructor(
    private patientService: PatientService,
    private router: Router
  ) {}

  savePatient() {
    this.patientService.create(this.patient).subscribe(() => {
      this.router.navigate(['/patients']); // retour liste
    });
  }
}
