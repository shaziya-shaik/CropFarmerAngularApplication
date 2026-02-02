package com.microservices.orders.orderService.orderServiceImp;

import com.microservices.orders.FeignClients.CartFeignClient.CartFeignClient;
import com.microservices.orders.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.orders.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.orders.FeignClients.cartDTO.cartItemsDTO.CartItemsResponseDTO;
import com.microservices.orders.FeignClients.cropFeignClient.CropFeignClient;
import com.microservices.orders.FeignClients.cropsDTO.CropResponseDTO;
import com.microservices.kafka.OrderEvent;
import com.microservices.orders.kafkaProducer.OrderEventProducer;
import com.microservices.kafka.OrderItemEvent;
import com.microservices.orders.orderDTO.OrderResponseDTO;
import com.microservices.orders.orderDTO.orderItemsDTO.OrderItemsResponseDTO;
import com.microservices.orders.orderModel.OrderItem;
import com.microservices.orders.orderModel.OrderModel;
import com.microservices.orders.orderRepository.OrderRepo;
import com.microservices.orders.orderService.OrderService;
import com.microservices.orders.statusEnum.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepo repo;
    private final CropFeignClient cropFeignClient;
    private final CartFeignClient cartFeignClient;
    private final OrderEventProducer orderEventProducer;

    public OrderServiceImp(OrderRepo repo,
                           CropFeignClient cropFeignClient,
                           CartFeignClient cartFeignClient, OrderEventProducer orderEventProducer) {
        this.repo = repo;
        this.cropFeignClient = cropFeignClient;
        this.cartFeignClient = cartFeignClient;
        this.orderEventProducer = orderEventProducer;
    }

    @Override
    public CartResponseDTO getCartByUser(String userEmailId) {
        return cartFeignClient.getCartByUser(userEmailId);
    }

    @Override
    public void addCartItems(CartItemsRequestDTO cartItemsRequestDTO, String userEmailId) {
        cartFeignClient.addCartItems(cartItemsRequestDTO, userEmailId);
    }

    @Override
    public List<OrderResponseDTO> getOrderItems(String userEmailId) {
        List<OrderModel> orders = repo.findByUserEmailId(userEmailId);
        List<OrderResponseDTO> response = new ArrayList<>();

        for (OrderModel order : orders) {
            response.add(mapToOrderResponseDTO(order));
        }

        return response;


    }

private OrderResponseDTO mapToOrderResponseDTO(OrderModel order) {
    List<OrderItemsResponseDTO> orderItems = new ArrayList<>();
    for(OrderItem orderItem : order.getOrderItems()) {
        orderItems.add(mapToOrderItemResponseDTO(orderItem));

    }
    return new OrderResponseDTO(order.getOrderId(), order.getUserEmailId(), order.getOrderDate() , order.getOrderStatus(), order.getTotalAmount() , orderItems);
}

private OrderItemsResponseDTO mapToOrderItemResponseDTO(OrderItem orderItem) {
    return new OrderItemsResponseDTO(orderItem.getOrderItemId() , orderItem.getCropId() , orderItem.getCropName(), orderItem.getQuantity(),  orderItem.getPriceAtOrderTime() , orderItem.getSubTotal());
}

@Override
public boolean deleteCartItem(long cartItemId, String userEmailId) {
    return cartFeignClient.deleteCartItem(cartItemId, userEmailId);
}

@Override
public void placeOrder(String userEmailId) {
    CartResponseDTO cart = cartFeignClient.getCartByUser(userEmailId);

    if (cart == null || cart.items().isEmpty()) {
        throw new RuntimeException("Cart is empty");
    }
    OrderModel order=mapToOrderModel(cart);
        repo.save(order);
    List<OrderItemEvent> itemEvents = new ArrayList<>();
    for (OrderItem item : order.getOrderItems()) {
        itemEvents.add(
                new OrderItemEvent(item.getCropId(), item.getQuantity())
        );
    }

    // 🔥 Send Kafka event
    orderEventProducer.sendOrderPlaced(
            new OrderEvent(
                    order.getOrderId(),
                    "ORDER_PLACED",
                    itemEvents
            )
    );

    cartFeignClient.deleteCart(userEmailId);
}

private OrderItem mapToOrderItem(
        OrderItemsResponseDTO dto,
        OrderModel order) {

    OrderItem item = new OrderItem();
    item.setCropId(dto.cropId());
    item.setCropName(dto.cropName());
    item.setQuantity(dto.quantity());
    item.setPriceAtOrderTime(dto.priceAtOrderTime());
    item.setSubTotal(dto.subTotal());
    item.setOrder(order);

    return item;
}

private OrderModel mapToOrderModel(CartResponseDTO cart) {
    OrderModel order = new OrderModel();
    order.setUserEmailId(cart.userEmailId());
    order.setOrderStatus(Status.CREATED);
    order.setOrderDate(LocalDateTime.now());
    order.setTotalAmount(cart.totalPrice());
    order.setOrderItems(mapCartItemsToOrderItemsDTO(cart.items() , order));

    return order;
}

    private List<OrderItem> mapCartItemsToOrderItemsDTO(List<CartItemsResponseDTO> items, OrderModel order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemsResponseDTO dto : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCropId(dto.cropId());
            orderItem.setPriceAtOrderTime(dto.price());
            orderItem.setSubTotal(dto.subTotal());
            orderItem.setQuantity(dto.quantity());
            CropResponseDTO crop = cropFeignClient.getCropById(dto.cropId());
            orderItem.setCropName(crop.cropName());
            orderItem.setOrder(order);

             orderItems.add( orderItem ); }

            return orderItems;
    }

    @Override
    public void cancelOrder(Long orderId) {

        OrderModel order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() == Status.DELIVERED) {
            throw new RuntimeException("Order already delivered");
        }

        order.setOrderStatus(Status.CANCELLED);
        repo.save(order);

        // 🔥 Send restore inventory event
        List<OrderItemEvent> itemEvents = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            itemEvents.add(
                    new OrderItemEvent(item.getCropId(), item.getQuantity())
            );
        }

        orderEventProducer.sendOrderPlaced(
                new OrderEvent(
                        order.getOrderId(),
                        "ORDER_CANCELLED",
                        itemEvents
                )
        );
    }


}

