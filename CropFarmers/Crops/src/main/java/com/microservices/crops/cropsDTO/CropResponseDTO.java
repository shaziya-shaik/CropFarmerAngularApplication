package com.microservices.crops.cropsDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservices.crops.cropsModel.FarmerCropModel;


public record CropResponseDTO(long cropId, String cropName, int quantity, double price) {
}
