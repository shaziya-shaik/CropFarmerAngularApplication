package com.microservices.crops.cropsDTO.FarmerCropDTOs;

import com.microservices.crops.cropsDTO.CropResponseDTO;
import com.microservices.crops.cropsModel.CropModel;

import java.util.List;

public record FarmerCropResponseDTO(String farmerEmailId , List<CropResponseDTO> crops) {
}
