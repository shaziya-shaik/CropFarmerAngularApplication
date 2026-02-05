package com.microservices.crops.cropsDTO;


import com.microservices.crops.cropsModel.FarmerCropModel;

public record CropRequestDTO(String cropName, int quantity, double price) {
}
