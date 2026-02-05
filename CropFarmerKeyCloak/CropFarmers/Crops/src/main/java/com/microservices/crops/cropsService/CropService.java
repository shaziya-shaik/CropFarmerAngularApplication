package com.microservices.crops.cropsService;

import com.microservices.crops.cropsDTO.CropRequestDTO;
import com.microservices.crops.cropsDTO.CropResponseDTO;
import com.microservices.crops.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropService {
    List<CropResponseDTO> getAllCrops();

    FarmerCropResponseDTO getCropByFarmerEmailId(String farmerEmailId);

    boolean addCrop(CropRequestDTO cropRequestDTO , String farmerEmailId);

    boolean decreaseOrDeleteCrop(long cropId );
    void createFarmer(String farmerEmailId);

     CropResponseDTO getCropById(long cropId);

    boolean deleteCrop(long cropId);
}
