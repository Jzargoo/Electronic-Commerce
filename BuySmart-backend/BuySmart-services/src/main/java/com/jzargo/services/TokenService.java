package com.jzargo.services;
import com.jzargo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(
                        ( (User)authentication.getPrincipal() )
                                .getId().toString()
                )
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .issuer("self")
                .claim("scope", scope)
                .build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet));

        return jwt.getTokenValue();
    }
}
