package com.microservices.crops.cropRepository;

import com.microservices.crops.cropsModel.CropModel;
import com.microservices.crops.cropsModel.FarmerCropModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerCropsRepo extends JpaRepository<FarmerCropModel, String> {
    Optional<FarmerCropModel> findByFarmerEmailId(String farmerEmailId);


}
