import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Patient } from '../../models/patient';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private apiUrl =
    'http://clinicappEcsAlbBackend-365106331.us-east-1.elb.amazonaws.com/api/patients';

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Patient[]>(this.apiUrl);
  }

  getById(id: number) {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  create(patient: Patient) {
    return this.http.post(this.apiUrl, patient);
  }

  update(id: number, data: Patient) {
    return this.http.put(`${this.apiUrl}/${id}`, data);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
