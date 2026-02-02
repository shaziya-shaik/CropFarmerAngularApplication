package com.microservices.login.loginController;

import com.microservices.login.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.login.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.login.FeignClients.cropFeignClient.CropFeignClient;
import com.microservices.login.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.login.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.login.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.login.loginDTO.LoginRequestDTO;
import com.microservices.login.loginDTO.LoginResponseDTO;
import com.microservices.login.loginEnum.loginRegister.RegisterStatus;
import com.microservices.login.loginService.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")

public class LoginController {


        private final LoginService service;
    private final CropFeignClient cropFeignClient;


    public LoginController(LoginService service, CropFeignClient cropFeignClient) {
        this.service = service;
        this.cropFeignClient = cropFeignClient;
    }





    @PostMapping("/")
    public ResponseEntity<String> Register(@RequestBody LoginRequestDTO loginRequestDTO) {
        RegisterStatus status = service.register(loginRequestDTO);
        switch(status)
        {
            case LOGIN_SUCCESS:
                return ResponseEntity.status(HttpStatus.OK) .body("Hi, " + loginRequestDTO.emailId() + " Welcome back to crop Farmers");
                case WRONG_PASSWORD: return ResponseEntity.status(HttpStatus.UNAUTHORIZED) .body("Hi, " + loginRequestDTO.emailId() + " Please enter correct password");
                case NEW_USER_CREATED: return ResponseEntity.status(HttpStatus.CREATED) .body("Register successful. Hi, " + loginRequestDTO.emailId() + " Welcome to crop Farmers");
            default: return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error"); } }

    @GetMapping("/")
    public ResponseEntity<List<LoginResponseDTO>> Registered(){
        List<LoginResponseDTO> loginResponseDTOS = service.Registered();
        return ResponseEntity.ok(loginResponseDTOS);
    }

    @GetMapping("/crops/farmer")
    public ResponseEntity<FarmerCropResponseDTO> getFarmerCrops(
            @RequestParam String farmerEmailId) {

        return ResponseEntity.ok(
                service.getMyCrops(farmerEmailId)
        );
    }

    @DeleteMapping("/crops/crop")
    public ResponseEntity<Void> decreaseOrDeleteCrops(@RequestParam long cropId) {
        boolean isDeleted = service.decreaseOrDeleteCrops(cropId );
        if(isDeleted){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("crops/AddCrop")
    public ResponseEntity<Void> addUserCrops(@RequestBody CropRequestDTO cropRequestDTO , @RequestParam String farmerEmailId) {
        boolean isAdded = service.addUserCrops(cropRequestDTO ,  farmerEmailId);
        if(isAdded){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/crops")
    public ResponseEntity<List<CropResponseDTO>> BrowseCrops(){
        return new ResponseEntity<>(service.BrowseCrops(), HttpStatus.OK);
    }

    @GetMapping("/cart/cartItems")
    public ResponseEntity<CartResponseDTO> getCartByUser(@RequestParam String userEmailId) {
        return  new ResponseEntity<>(service.getCartByUser(userEmailId), HttpStatus.OK);

    }

    @PostMapping("/cart/cartItems")
    public ResponseEntity<Void> addCartItems(@RequestBody CartItemsRequestDTO cartItemsRequestDTO, @RequestParam String userEmailId) {
        service.addCartItems(cartItemsRequestDTO , userEmailId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart/deleteItem/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(@PathVariable long cartItemId, @RequestParam String userEmailId) {
        boolean isDeleted = service.deleteCartItem(cartItemId, userEmailId);
        if(isDeleted){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }



}
