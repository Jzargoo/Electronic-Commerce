package com.jzargo.mapper;

import com.jzargo.dto.CartDto;
import com.jzargo.entity.Cart;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class CartCreateMapper implements Mapper<CartDto, Cart>{
    private final UserRepository userRepository;
    public CartCreateMapper(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Cart map(CartDto FromObject, Cart RawObject) {
        return Mapper.super.map(FromObject, RawObject);
    }

    @Override
    public Cart map(CartDto object) {
        return Cart.builder()
                .buyer(userRepository.findById(
                        object.getBuyerId()).orElseThrow(RuntimeException::new))
                .build();
    }

}
