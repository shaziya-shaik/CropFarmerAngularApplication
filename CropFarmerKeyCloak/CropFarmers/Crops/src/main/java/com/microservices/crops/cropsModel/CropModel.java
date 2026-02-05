package com.microservices.crops.cropsModel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crops")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cropId;
    private String cropName;

    private double price;
    @ManyToOne
    @JoinColumn(name = "farmer_email_id", nullable = false)
    @JsonBackReference
    private FarmerCropModel farmer;
}
