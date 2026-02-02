package com.microservices.cart.cartService.cartServiceImp;

import com.microservices.cart.FeignClients.cropFeignClient.CropFeignClient;
import com.microservices.cart.FeignClients.cropsDTO.CropRequestDTO;
import com.microservices.cart.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.cart.FeignClients.cropsDTO.FarmerCropDTOs.FarmerCropResponseDTO;
import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsResponseDTO;
import com.microservices.cart.cartDTO.CartResponseDTO;
import com.microservices.cart.cartModel.CartItems;
import com.microservices.cart.cartModel.CartModel;
import com.microservices.cart.cartRepository.CartRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements com.microservices.cart.cartService.CartService {

        private final CartRepo repo;

        private  final CropFeignClient crop;


        public CartService(CartRepo repo, CropFeignClient crop) {
            this.repo = repo;
            this.crop = crop;
        }

    @Override
    public CartResponseDTO getCartItems(String userEmailId) {

        return repo.findByUserEmailId(userEmailId)
                .map(this::mapCartResponseDTO)
                .orElseGet(() -> new CartResponseDTO(0, userEmailId, 0.0, new ArrayList<>()));

    }

    private CartResponseDTO mapCartResponseDTO(CartModel cart) {
            List<CartItemsResponseDTO> cartItemsResponseDTO=new ArrayList<>();
            for (CartItems items:cart.getItems()) {
                cartItemsResponseDTO.add(mapCartItemsResponseDTO(items));
            }
            return new CartResponseDTO(cart.getCartId(),  cart.getUserEmailId() , cart.getTotalPrice() , cartItemsResponseDTO);
    }

    @Override
    public void createCart(String userEmailId) {
        CartModel newCart = new CartModel();
        newCart.setUserEmailId(userEmailId);
        newCart.setTotalPrice(0);
        newCart.setItems(new ArrayList<>());
        repo.save(newCart);
    }

    @Override
    public void addCartItems(CartItemsRequestDTO dto, String userEmailId) {

        CartModel cart = repo.findByUserEmailId(userEmailId)
                .orElseGet(() -> {
                    CartModel newCart = new CartModel();
                    newCart.setUserEmailId(userEmailId);
                    newCart.setItems(new ArrayList<>());
                    newCart.setTotalPrice(0);
                    return repo.save(newCart); // ✅ SAVE IMMEDIATELY
                });

        List<CropResponseDTO> crops = crop.getAllCrops();

        CropResponseDTO cropData = crops.stream()
                .filter(c -> c.cropId() == dto.cropId())
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NO_CONTENT, "Crop not found"
                ));

        for (CartItems item : cart.getItems()) {

            if (item.getCropId() == dto.cropId()) {

                int newQuantity = item.getQuantity() + dto.quantity();

                if (newQuantity > cropData.quantity()) {
                    throw new ResponseStatusException(
                            HttpStatus.NO_CONTENT,
                            "Insufficient quantity available"
                    );
                }

                item.setQuantity(newQuantity);
                item.setSubTotal(newQuantity * cropData.price());
                updateTotalPrice(cart);
                repo.save(cart);
                return;
            }
        }

        // New item
        if (dto.quantity() > cropData.quantity()) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "Insufficient quantity available"
            );
        }

        CartItems newItem = new CartItems();
        newItem.setCropId(cropData.cropId());
        newItem.setPrice(cropData.price());
        newItem.setQuantity(dto.quantity());
        newItem.setSubTotal(dto.quantity() * cropData.price());
        newItem.setCart(cart);

        cart.getItems().add(newItem);
        updateTotalPrice(cart);
        repo.save(cart);
    }



    @Override
    public boolean deleteCartItem(long cartItemId, String userEmailId) {

        CartModel cart = repo.findByUserEmailId(userEmailId).orElse(null);
        if (cart == null) {
            return false;
        }

        CartItems targetItem = null;

        for (CartItems item : cart.getItems()) {
            if (item.getCartItemId() == cartItemId) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            return false;
        }


        if (targetItem.getQuantity() > 1) {
            targetItem.setQuantity(targetItem.getQuantity() - 1);
            targetItem.setSubTotal(targetItem.getQuantity() * targetItem.getPrice());
        } else {
            // orphanRemoval = true will delete from DB
            cart.getItems().remove(targetItem);
        }

        updateTotalPrice(cart);
        repo.save(cart);

        return true;
    }

    private void updateTotalPrice(CartModel cart) {
        double total = 0;
        for (CartItems item : cart.getItems()) {
            total += item.getSubTotal();
        }
        cart.setTotalPrice(total);
    }

    @Override
    public void deleteCart(String userEmailId ) {
           CartResponseDTO cart= getCartItems(userEmailId);

            repo.deleteById(cart.cartId() );
            return;
    }




    private CartItemsResponseDTO mapCartItemsResponseDTO(CartItems items) {
        CropResponseDTO cropData = crop.getAllCrops().stream()
                .filter(c -> c.cropId() == items.getCropId())
                .findFirst()
                .orElse(null);

        String cropName = cropData != null ? cropData.cropName() : "Unknown Crop";

        return new CartItemsResponseDTO(
                items.getCartItemId(),
                items.getCropId(),
                cropName,
                items.getQuantity(),
                items.getPrice(),
                items.getSubTotal()
        );
}
}
