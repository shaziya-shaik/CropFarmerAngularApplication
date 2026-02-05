package com.microservices.login.loginService;

import com.microservices.login.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.login.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.login.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.login.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.login.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.login.loginDTO.LoginRequestDTO;
import com.microservices.login.loginDTO.LoginResponseDTO;
import com.microservices.login.loginEnum.loginRegister.RegisterStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoginService {

    public RegisterStatus register(LoginRequestDTO loginRequestDTO);

    public List<LoginResponseDTO> Registered();

    public FarmerCropResponseDTO getMyCrops(String emailId);

    boolean decreaseOrDeleteCrops( long cropId );

    boolean addUserCrops(CropRequestDTO cropRequestDTO , String farmerEmailId);

    List<CropResponseDTO> BrowseCrops();

    CartResponseDTO getCartByUser(String userEmailId);

    void addCartItems(CartItemsRequestDTO cartItemsRequestDTO, String userEmailId);

    boolean deleteCartItem(long cartItemId, String userEmailId);
}
