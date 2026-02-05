package com.microservices.crops.cropsModel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "FarmerCrops")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerCropModel {
    @Id
    private String farmerEmailId;
    @OneToMany(mappedBy = "farmer" , cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<CropModel> crops;
}
