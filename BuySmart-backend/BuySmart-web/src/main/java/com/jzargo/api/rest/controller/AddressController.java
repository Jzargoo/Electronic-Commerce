package com.jzargo.api.rest.controller;

import com.jzargo.services.AddressService;
import com.jzargo.shared.model.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService= addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses(Authentication auth){
        Long l = Long.valueOf(((Jwt)
                auth.getPrincipal()).getSubject());
        List<AddressDto> list = addressService.getAddresses(l);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto addressDto, Authentication auth) {
        try {

        Long principal = Long.valueOf(((Jwt) auth.getPrincipal())
                .getSubject());
        addressDto.setUserId(principal);
        // Here a new address because address might not save so that guarantee
        AddressDto address = addressService.addAddress(addressDto);
        address.setUserId(principal);
        return ResponseEntity.ok(address);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
