package com.jzargo.api.rest.controller;

import com.jzargo.exceptions.UserAlreadyExistsException;
import com.jzargo.services.EmailService;
import com.jzargo.services.TokenService;
import com.jzargo.services.UserService;
import com.jzargo.shared.model.LoginCreateDto;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtDecoder jwtDecoder;

    public AuthController(TokenService tokenService, EmailService emailService, UserService userService, AuthenticationManager authenticationManager, JwtDecoder jwtDecoder) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/token")
    public String getToken(Authentication authentication){
        return tokenService.generateToken(authentication);
    }
    @PostMapping("/send/reset")
    public ResponseEntity<Void> SendReset(String targetEmail) {
        String token = emailService.createToken(targetEmail);
        String bodyHtml = """
                    <html>
                        <body style="font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; padding: 20px;">
                            <div style="background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                                <h2 style="color: #4CAF50;">Hello!</h2>
                                <p>You requested a password reset for your account. To continue, please click the following link:</p>
                                <p>
                                   <form action="https://localhost:4200/reset" method="post" style="display: inline;">
                                         <input type="hidden" name="token" value="%s">
                                         <button type="submit" style="color: #4CAF50; text-decoration: none; font-weight: bold; background: none; border: none; cursor: pointer;">
                                             Reset Password
                                         </button>
                                     </form>
                                </p>
                                <p>If you did not request a password reset, please ignore this email.</p>
                                <p>Best regards,<br/>The BuySmart Support Team</p>
                            </div>
                        </body>
                    </html>
                    """.formatted(token);
        String subject = "Password Reset Request";
        emailService.sendEmail(targetEmail, subject, bodyHtml);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestParam("token") String token,
                                        @RequestParam("password") String newPassword,
                                        @RequestParam("email") String email) {
        if (emailService.verifyToken(token, email)
                && userService.updatePassword(newPassword, email)) {
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.badRequest().body("Token is invalid or expired");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginCreateDto login) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = getToken(auth);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateAndUpdateDto userDto) {
        try {
            userService.create(userDto);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(),
                            userDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = tokenService.generateToken(auth);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
    @GetMapping("/checkPermission")
    public ResponseEntity<String> checkPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ||
                !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("GUEST");
        } else {
            return ResponseEntity.ok().body(((Jwt)authentication.getPrincipal())
                    .getClaim("scope"));
        }

    }
}