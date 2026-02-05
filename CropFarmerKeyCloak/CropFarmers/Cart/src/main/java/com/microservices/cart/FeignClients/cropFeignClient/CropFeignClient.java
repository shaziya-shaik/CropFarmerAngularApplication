package com.microservices.cart.FeignClients.cropFeignClient;


import com.microservices.cart.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.cart.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.cart.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CROPS-SERVICE" , url="http://localhost:8082")
public interface CropFeignClient {

    @GetMapping("/crops/farmer")
    FarmerCropResponseDTO getCropsByFarmerEmail(
            @RequestParam String farmerEmailId
    );


    @GetMapping("/crops")
    List<CropResponseDTO> getAllCrops();

}
