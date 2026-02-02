import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { CropService } from '../../service/services/Cropservices/crop.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-crop',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './add-crop.component.html',
  styleUrls: ['./add-crop.component.css']
})
export class AddCropComponent implements OnInit {

  crop = {
    cropName: '',
    quantity: null as number | null,
    price: null as number | null,
    farmerEmailId: localStorage.getItem('username') || ''
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cropService: CropService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['name']) this.crop.cropName = params['name'];
      if (params['quantity']) this.crop.quantity = +params['quantity'];
      if (params['price']) this.crop.price = +params['price'];
    });
  }

  submitCrop() {
    if (!this.crop.farmerEmailId) {
      alert('Farmer email is missing!');
      return;
    }

    this.cropService.addCrop(this.crop,this.crop.farmerEmailId).subscribe({
      next: () => {
        alert('Crop saved successfully');
        this.router.navigate(['/my-crops']);
      },
      error: (err) => {
        console.error('Error saving crop:', err);
        alert('Failed to save crop');
      }
    });
  }
}
