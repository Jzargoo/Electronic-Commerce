package com.jzargo.api.rest.controller;

import com.jzargo.services.EmailService;
import com.jzargo.services.TokenService;
import com.jzargo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserService userService;

    public AuthController(TokenService tokenService, EmailService emailService, UserService userService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userService=userService;
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
        if (emailService.verifyToken(token, email)) {
            userService.updatePassword(newPassword, email);
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.badRequest().body("Token is invalid or expired");
    }
}