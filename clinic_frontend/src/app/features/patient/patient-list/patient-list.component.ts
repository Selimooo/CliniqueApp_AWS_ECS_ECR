import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../../services/patient/patient.service';
import { Patient } from '../../../models/patient';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './patient-list.component.html',
  styleUrl: './patient-list.component.css',
})
export class PatientListComponent implements OnInit {
  patients: Patient[] = [];
  constructor(private patientService: PatientService,) {}

  ngOnInit(): void {
    this.patientService.getAll().subscribe(data => {
      this.patients = data as Patient[];
    });
  }
}
