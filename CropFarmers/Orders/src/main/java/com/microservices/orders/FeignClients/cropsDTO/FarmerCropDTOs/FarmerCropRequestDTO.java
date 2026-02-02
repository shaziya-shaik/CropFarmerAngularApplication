package com.microservices.orders.FeignClients.cropsDTO.FarmerCropDTOs;



import com.microservices.orders.FeignClients.cropsDTO.CropResponseDTO;

import java.util.List;

public record FarmerCropRequestDTO(List<CropResponseDTO> farmerCrop) {
}
