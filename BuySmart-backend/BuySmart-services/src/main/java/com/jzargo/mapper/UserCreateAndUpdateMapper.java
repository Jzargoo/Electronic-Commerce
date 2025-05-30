package com.jzargo.mapper;

import com.jzargo.common.Role;
import com.jzargo.entity.User;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import org.springframework.stereotype.Component;


@Component
public class UserCreateAndUpdateMapper implements Mapper<UserCreateAndUpdateDto, User>{

    @Override
    public User map(UserCreateAndUpdateDto object) {
        // It was the builder that creates empty lists, not the constructor which sets them to null.
        // So builder just more comfortable because I do not need to write new ArrayList
        return getBuild(object, User.builder().build());
    }

    private User getBuild(UserCreateAndUpdateDto object, User user) {
        user.setUsername(object.getUsername());
        user.setPassword(object.getPassword());
        user.setEmail(object.getEmail());
        user.setPhone(object.getPhone());
        user.getRoles().add(Role.valueOf(
                object.getRole())
        );
        return user;
    }

    @Override
    public User map(UserCreateAndUpdateDto FromObject, User RawObject) {
        var user = getBuild(FromObject,RawObject);
        user.setOrders(RawObject.getOrders());
        user.setOwnProducts(
               RawObject.getOwnProducts()
        );
        user.setCart(RawObject.getCart());
        return user;
    }
}
