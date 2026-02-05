package com.microservices.orders.FeignClients.cropFeignClient;



import com.microservices.orders.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.orders.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.orders.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
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

    @DeleteMapping("/crops/crop")
    ResponseEntity<Void> decreaseOrDeleteCrop(
            @RequestParam long cropId);


    @PostMapping("/crops/AddCrop")
    ResponseEntity<Void> addCrop(@RequestBody CropRequestDTO cropRequestDTO , @RequestParam String farmerEmailId);

    @GetMapping("/crops")
    List<CropResponseDTO> getAllCrops();

    @GetMapping("/crops/cropId")
     CropResponseDTO getCropById(@RequestParam long cropId);

}
