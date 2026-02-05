package com.microservices.login.loginService.loginServiceImp;

import com.microservices.login.FeignClients.CartFeignClient.CartFeignClient;
import com.microservices.login.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.login.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.login.FeignClients.cropFeignClient.CropFeignClient;
import com.microservices.login.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.login.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.login.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.login.loginDTO.LoginRequestDTO;
import com.microservices.login.loginDTO.LoginResponseDTO;
import com.microservices.login.loginEnum.loginRegister.RegisterStatus;
import com.microservices.login.loginEnum.loginRole.LoginRole;
import com.microservices.login.loginModel.LoginModel;
import com.microservices.login.loginRepository.LoginRepo.LoginRepo;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements com.microservices.login.loginService.LoginService {


    private final LoginRepo repo;
    private  final CropFeignClient cropFeignClient;
    private final CartFeignClient cartFeignClient;


    public LoginService(LoginRepo repo, CropFeignClient cropFeignClient, CartFeignClient cartFeignClient) {
        this.repo = repo;
        this.cropFeignClient = cropFeignClient;
        this.cartFeignClient = cartFeignClient;
    }

    @Override
    public RegisterStatus register(LoginRequestDTO loginRequestDTO) {
        Optional<LoginModel> user = repo.findByEmailId(loginRequestDTO.emailId());
        if(user.isPresent()) {
            if(user.get().getPassword().equals(loginRequestDTO.password()))
            { return RegisterStatus.LOGIN_SUCCESS; }
            return RegisterStatus.WRONG_PASSWORD; }
        LoginModel loginModel = mapLoginModel(loginRequestDTO);
        repo.save(loginModel);
        LoginRole role = loginModel.getRole();

        try {
            if(role == LoginRole.ADMIN || role == LoginRole.FARMER) {
                cropFeignClient.createFarmer(loginRequestDTO.emailId());
            }
            if(role == LoginRole.ADMIN || role == LoginRole.USER){
                cartFeignClient.createCart(loginRequestDTO.emailId());
            }
        } catch(Exception e) {
            // log error but don't crash registration
            System.out.println("Feign client failed: " + e.getMessage());
        }
        return RegisterStatus.NEW_USER_CREATED; }



    @Override
    public List<LoginResponseDTO> Registered() {
        List<LoginResponseDTO> loginResponseDTOS = new ArrayList<>();
        for (LoginModel loginModel : repo.findAll()) {
            LoginResponseDTO loginResponseDTO = mapLoginResponse(loginModel);
            loginResponseDTOS.add(loginResponseDTO);

        }
        return loginResponseDTOS;

    }
    @Override
    public FarmerCropResponseDTO getMyCrops(String emailId) {
        return getFarmerCrops(emailId);
    }
    @Override
    public boolean decreaseOrDeleteCrops( long cropId ) {
        return decreaseOrDeleteCrop( cropId);
    }

    @Override
    public boolean addUserCrops(CropRequestDTO cropRequestDTO , String farmerEmailId) {
        return addCrop(cropRequestDTO,  farmerEmailId);
    }


    @Override
    public List<CropResponseDTO> BrowseCrops() {
       return cropFeignClient.BrowseCrops();
    }

    @Override
    public CartResponseDTO getCartByUser(String userEmailId) {
        return cartFeignClient.getCartByUser(userEmailId);
    }

    @Override
    public void addCartItems(CartItemsRequestDTO cartItemsRequestDTO, String userEmailId) {
         cartFeignClient.addCartItems(cartItemsRequestDTO , userEmailId);
         return;
    }

    @Override
    public boolean deleteCartItem(long cartItemId, String userEmailId) {
       boolean isDeleted= cartFeignClient.deleteCartItem(cartItemId , userEmailId);
       if(isDeleted){
           return true;
       }
        return false;
    }


    public boolean addCrop(CropRequestDTO dto, String farmerEmailId) {

        try {
            cropFeignClient.addCrop(dto,  farmerEmailId);
            return true; // deleted
        } catch (FeignException.NotFound e) {
            return false; // crop not found
        }
    }


    public FarmerCropResponseDTO getFarmerCrops(String farmerEmailId) {
        return cropFeignClient.getCropsByFarmerEmail(farmerEmailId);
    }


    public boolean decreaseOrDeleteCrop( long cropId ) {
        try {
            cropFeignClient.decreaseOrDeleteCrop(cropId);
            return true; // deleted
        } catch (FeignException.NotFound e) {
            return false; // crop not found
        }
    }


    private LoginModel mapLoginModel(LoginRequestDTO loginRequestDTO) {
        LoginModel loginModel = new LoginModel();
        loginModel.setEmailId(loginRequestDTO.emailId());
        loginModel.setPassword(loginRequestDTO.password());
        loginModel.setRole(loginRequestDTO.role());
        return loginModel;

    }

    private LoginResponseDTO mapLoginResponse(LoginModel loginModel) {

            return new LoginResponseDTO(loginModel.getEmailId(), loginModel.getPassword(), loginModel.getRole());

    }


}
