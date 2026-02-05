package com.microservices.crops.cropsController;

import com.microservices.crops.cropsDTO.CropRequestDTO;
import com.microservices.crops.cropsDTO.CropResponseDTO;
import com.microservices.crops.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.crops.cropsService.CropService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/crops")
public class CropController {

    private final CropService service;

    public CropController(CropService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<CropResponseDTO>> BrowseCrops() {
       return  ResponseEntity.ok(service.getAllCrops());
    }

    @PostMapping
    public ResponseEntity<Void> createFarmer(String farmerEmailId) {
        service.createFarmer(farmerEmailId);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cropId")
    public ResponseEntity<CropResponseDTO> getCropById(@RequestParam long cropId) {
        return  ResponseEntity.ok(service.getCropById(cropId));
    }

    @GetMapping("/farmer")
    public ResponseEntity<FarmerCropResponseDTO> getCropByFarmerEmailId(@RequestParam String farmerEmailId) {
        FarmerCropResponseDTO crops= service.getCropByFarmerEmailId(farmerEmailId);
        if(crops == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(crops);
    }

    @PostMapping("/AddCrop")
    public ResponseEntity<Void> addCrop(@RequestBody CropRequestDTO cropRequestDTO , @RequestParam String farmerEmailId) {
       boolean isPresent= service.addCrop(cropRequestDTO , farmerEmailId);
       if (isPresent) {
           return ResponseEntity.ok().build();
       }
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }

    @DeleteMapping("/crop")
    public ResponseEntity<Void> decreaseOrDeleteCrop(@RequestParam long cropId ) {
        boolean cropFound =service.decreaseOrDeleteCrop(cropId ) ;
        if (cropFound) {
            return  ResponseEntity.status(HttpStatus.OK).build();
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/farmer/crop")
    public ResponseEntity<Void> deleteCrop(@RequestParam long cropId ) {
        boolean cropFound =service.deleteCrop(cropId ) ;
        if (cropFound) {
            return  ResponseEntity.status(HttpStatus.OK).build();
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }






}
