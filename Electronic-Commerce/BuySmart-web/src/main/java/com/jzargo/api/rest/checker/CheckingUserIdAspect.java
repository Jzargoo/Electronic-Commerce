package com.jzargo.api.rest.checker;

import com.jzargo.services.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.jzargo.common.Role.ADMIN;

@Aspect
@Component
public class CheckingUserIdAspect {
    private final UserService userService;

    public CheckingUserIdAspect(UserService userService) {
        this.userService = userService;
    }

    @Before("@annotation(com.jzargo.api.rest.checker.CheckUserId) && args( userId,..)")
    void checkUserId(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getName();

        Long id = userService.findByUsername(principal).getId();
        if(!id.equals(userId) && authentication.getAuthorities().contains(ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}

