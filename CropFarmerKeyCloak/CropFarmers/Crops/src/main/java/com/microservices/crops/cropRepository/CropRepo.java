package com.microservices.crops.cropRepository;


import com.microservices.crops.cropsDTO.CropResponseDTO;
import com.microservices.crops.cropsModel.CropModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepo extends JpaRepository<CropModel, Long> {






}
