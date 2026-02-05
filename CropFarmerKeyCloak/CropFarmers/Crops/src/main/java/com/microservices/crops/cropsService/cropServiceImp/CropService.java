package com.microservices.crops.cropsService.cropServiceImp;


import com.microservices.crops.FeignClients.client.InventoryClient;
import com.microservices.crops.FeignClients.inventoryDTOs.InventoryResponseDTO;
import com.microservices.crops.cropRepository.CropRepo;
import com.microservices.crops.cropRepository.FarmerCropsRepo;
import com.microservices.crops.cropsDTO.CropRequestDTO;
import com.microservices.crops.cropsDTO.CropResponseDTO;
import com.microservices.crops.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.crops.cropsModel.CropModel;
import com.microservices.crops.cropsModel.FarmerCropModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CropService implements com.microservices.crops.cropsService.CropService {


    private final CropRepo repo;
    private final FarmerCropsRepo farmerCropsRepo;
    private  final InventoryClient inventoryClient;

    public CropService(CropRepo repo, FarmerCropsRepo farmerCropsRepo, InventoryClient inventoryClient) {
        this.repo = repo;
        this.farmerCropsRepo = farmerCropsRepo;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<CropResponseDTO> getAllCrops() {

       List<CropModel> crops = repo.findAll();
        List<CropResponseDTO> cropResponseDTOs = new ArrayList<>();
       for (CropModel crop : crops) {
           InventoryResponseDTO inventory = inventoryClient.getInventroyById(crop.getCropId());
           cropResponseDTOs.add(mapToCropResponseDTO(crop, inventory.availableQuantity()));

       }
     return cropResponseDTOs;

    }

    @Override
    public FarmerCropResponseDTO getCropByFarmerEmailId(String farmerEmailId) {
        FarmerCropModel farmerCrops= farmerCropsRepo.findByFarmerEmailId(farmerEmailId).orElse(null);
        if(farmerCrops!=null){
            return mapToFarmerResponseDTO(farmerCrops);
        }

        return null;

    }

    @Override
    public CropResponseDTO  getCropById(long cropId){
        CropModel crop = repo.findById(cropId).orElse(null);
        InventoryResponseDTO inventory=inventoryClient.getInventroyById(crop.getCropId());
        return mapToCropResponseDTO(crop,inventory.availableQuantity() );

    }

    @Override
    public boolean deleteCrop(long cropId) {
        if(repo.existsById(cropId)){
            repo.deleteById(cropId);
            inventoryClient.deleteInventory(cropId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addCrop(CropRequestDTO cropRequestDTO, String farmerEmailId) {

        FarmerCropModel farmer = farmerCropsRepo
                .findByFarmerEmailId(farmerEmailId)
                .orElse(null);

        if (farmer == null) {
            farmer = new FarmerCropModel();
            farmer.setFarmerEmailId(farmerEmailId);
            farmer.setCrops(new ArrayList<>());
            farmer = farmerCropsRepo.saveAndFlush(farmer);
        }

        // check if crop exists
        for (CropModel crop : farmer.getCrops()) {
            if (crop.getCropName().equalsIgnoreCase(cropRequestDTO.cropName())) {

                inventoryClient.createInventory(
                        crop.getCropId(),
                        cropRequestDTO.quantity()
                );
                CropModel cropModel = mapToCropModel(cropRequestDTO);


                return true;
            }
        }

        // 🆕 NEW CROP
        CropModel cropModel = mapToCropModel(cropRequestDTO);
        cropModel.setFarmer(farmer);
        farmer.getCrops().add(cropModel);
        cropModel = repo.saveAndFlush(cropModel);
        // ✅ SAVE & FLUSH so ID is generated
        farmerCropsRepo.saveAndFlush(farmer);

        // ✅ NOW cropId is available
        inventoryClient.createInventory(
                cropModel.getCropId(),
                cropRequestDTO.quantity()
        );


        return true;
    }


    @Override
    public boolean decreaseOrDeleteCrop(long cropId ) {
        CropModel crop = repo.findById(cropId).orElse(null);
        if(crop==null){
            return false;
        }
   int q=inventoryClient.reduceInventory(cropId , 1);
        if(q==0){
            repo.deleteById(cropId);
        }
        return true;

}



    @Override
    public void createFarmer(String farmerEmailId) {
        FarmerCropModel farmer = new FarmerCropModel();
        farmer.setFarmerEmailId(farmerEmailId);
        farmer.setCrops(new ArrayList<>());
        farmerCropsRepo.save(farmer);
    }


    private CropResponseDTO mapToCropResponseDTO(CropModel cropModel , int quantity) {
        return new CropResponseDTO(cropModel.getCropId(),
                cropModel.getCropName(), quantity , cropModel.getPrice() );
    }

    private CropModel mapToCropModel(CropRequestDTO cropRequestDTO) {
        CropModel crop=new CropModel();
        crop.setCropName(cropRequestDTO.cropName());

        crop.setPrice(cropRequestDTO.price());
        return crop;
    }

    private  FarmerCropResponseDTO mapToFarmerResponseDTO(FarmerCropModel farmerCrop) {
        List<CropResponseDTO> farmerCrops= new ArrayList<>();
        for(CropModel crop:farmerCrop.getCrops()){
            InventoryResponseDTO inventory=inventoryClient.getInventroyById(crop.getCropId());
            farmerCrops.add(mapToCropResponseDTO(crop ,inventory.availableQuantity()));
        }
        return new FarmerCropResponseDTO(farmerCrop.getFarmerEmailId() ,farmerCrops );
    }


}
