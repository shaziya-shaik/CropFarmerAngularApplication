import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CropService {

  private API_URL = 'http://localhost:8080/crops';

  constructor(private http: HttpClient) {}

  addCrop(payload: any, farmerEmailId: string): Observable<void> {
    const url = `${this.API_URL}/AddCrop?farmerEmailId=${encodeURIComponent(farmerEmailId)}`;
  return this.http.post<void>(url, payload);
  }

  getMyCrops(farmerEmailId: string): Observable<any[]> {
    return this.http.get<any>(`${this.API_URL}/farmer`, { params: { farmerEmailId } })
    .pipe(
       map(response => response.crops) 
    );
  }

  // ✅ Delete crop
  deleteCrop(cropId: number) {
    return this.http.delete(
      `${this.API_URL}/farmer/crop`,
      { params: { cropId } }
    );
  }

  

  
}
