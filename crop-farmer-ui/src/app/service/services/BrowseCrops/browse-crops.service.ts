import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BrowseCropsService {
  private API_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // ✅ Get all crops for users
  getAllCrops(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/crops`);
  }
  
}
