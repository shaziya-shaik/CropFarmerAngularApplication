package com.microservices.login.FeignClients.cropsDTO.FarmerCropDTOs;





import com.microservices.login.FeignClients.cropsDTO.CropResponseDTO;

import java.util.List;

public record FarmerCropResponseDTO(String farmerEmailId , List<CropResponseDTO> crops) {
}
