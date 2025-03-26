package com.jzargo;

import com.jzargo.api.rest.controller.UserController;
import com.jzargo.security.RSAKeyProperties;
import com.jzargo.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class Application{
    public static void main(String[] args) {
        var  context = SpringApplication.run(Application.class, args);
        Object bean = context.getBean(UserService.class);
        Object beanController = context.getBean(UserController.class);
        System.out.println(bean);
        System.out.println(beanController);
    }
}
