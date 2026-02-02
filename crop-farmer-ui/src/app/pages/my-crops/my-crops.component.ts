import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/services/auth.service';
import { CropService } from '../../service/services/Cropservices/crop.service';

@Component({
  selector: 'app-my-crops',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-crops.component.html',
  styleUrls: ['./my-crops.component.css']
})
export class MyCropsComponent implements OnInit {
  crops: any[] = [];
  farmerEmailId!:string;
  

    constructor(
    private auth: AuthService,
    private cropService: CropService,
    private router: Router
  ) {}

  ngOnInit() {
    this.farmerEmailId = this.auth.getUsername()!;

    this.cropService.getMyCrops(this.farmerEmailId).subscribe({
      
     next: (data) => this.crops = data ?? [],
      
      error: (err) => console.error('Error fetching crops', err)
    });
  }

  addCrop() {
    this.router.navigate(['/add-crop']);
  }

  editCrop(crop: any) {
    this.router.navigate(['/add-crop'], {
      queryParams: {
        name: crop.cropName,
        quantity: crop.quantity,
        price: crop.price
      }
    });
  }

 deleteCrop(crop: any) {
  this.cropService.deleteCrop(crop.cropId).subscribe({
    next: () => {
      // Instant UI update
      this.loadCrops();
      this.crops = this.crops.filter(c => c.cropId !== crop.cropId);
      alert(`✅ ${crop.cropName} deleted successfully`);
    },
    error: err => console.error('Delete failed', err)
  });
}
  private loadCrops() {
  this.cropService.getMyCrops(this.farmerEmailId).subscribe({
    next: data => this.crops = data ?? [],
    error: err => console.error('Error fetching crops', err)
  });
}
}
