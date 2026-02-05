package com.microservices.cart.FeignClients.cropsDTO.FarmerCropDTOs;




import com.microservices.cart.FeignClients.cropsDTO.CropResponseDTO;

import java.util.List;

public record FarmerCropResponseDTO(String farmerEmailId , List<CropResponseDTO> crops) {
}
