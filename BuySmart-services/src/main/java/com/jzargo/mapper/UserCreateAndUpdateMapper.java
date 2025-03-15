package com.jzargo.mapper;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.entity.User;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.OrderRepository;
import com.jzargo.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class UserCreateAndUpdateMapper implements Mapper<UserCreateAndUpdateDto, User>{
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public UserCreateAndUpdateMapper(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository, ProductRepository productRepository1) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository1;
    }

    @Override
    public User map(UserCreateAndUpdateDto object) {
        return getBuild(object, new User());
    }

    private User getBuild(UserCreateAndUpdateDto object, User user) {
        user.setUsername(object.getUsername());
        user.setPassword(object.getPassword());
        user.setPhone(object.getPhone());
        return user;
    }

    @Override
    public User map(UserCreateAndUpdateDto FromObject, User RawObject) {
        var user = getBuild(FromObject,RawObject);
        user.setOrders(orderRepository.findAllById(FromObject.getOrdersId()));
        user.setId(RawObject.getId());
        user.setCreatedTime(RawObject.getCreatedTime());
        user.setOwnProducts(
                productRepository.findAllById(FromObject.getOwnProductIds())
        );
        user.setCart(cartRepository.findById(FromObject.getCartId())
                .orElseThrow());
        return user;
    }
}
