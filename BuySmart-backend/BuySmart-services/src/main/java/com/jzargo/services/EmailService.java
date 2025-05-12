package com.jzargo.services;

import com.jzargo.entity.ResetPasswordToken;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.EmailException;
import com.jzargo.exceptions.EmailExistException;
import com.jzargo.repository.ResetPasswordTokenRepository;
import com.jzargo.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpMethod.POST;

@Service
@Transactional
public class EmailService {
    private final UserRepository userRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Value("${Email.api.url}")
    private String emailUrl;
    @Value("${Email.api.key}")
    private String key;
    @Value("${Email.sender.url}")
    private String senderUrl;

    public EmailService(UserRepository userRepository, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.userRepository = userRepository;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @SneakyThrows
    public void sendEmail(String targetEmail, String subject, String body) {
        RestTemplate restTemplate = new RestTemplate();

        String jsonBody = """
                {
                "from": "%s",
                "to": "%s",
                "subject": "%s",
                "bodyText": "%s",
                "bodyHtml": "%s"
                }
                """.formatted(senderUrl, targetEmail, subject, body, body);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + key);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(emailUrl, POST, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new EmailException("Failed to send email: " + response.getStatusCode());
        } else {
            System.out.println("Email sent successfully: " + response.getBody());
        }
    }

    @SneakyThrows
    public String createToken(String targetEmail) {
        Long id = userRepository.findByEmail(targetEmail).orElseThrow(() ->
                new DataNotFoundException("User with " + targetEmail + "do not exist")).getId();

        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("User with " + targetEmail + "do not exist"));
        if(
                resetPasswordToken.getExpiryDate().isBefore(LocalDateTime.now().plusMinutes(30))
        ){
            resetPasswordToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
            resetPasswordToken.setToken(UUID.randomUUID().toString());
            resetPasswordTokenRepository.saveAndFlush(resetPasswordToken);
            return resetPasswordToken.getToken();
        } else {
            throw new EmailExistException("Token already exist");
        }
    }


    @SneakyThrows
    public boolean verifyToken(String token, String email) {

        ResetPasswordToken resetPasswordToken =
                resetPasswordTokenRepository.findByToken(token).orElseThrow(
                        () -> new DataNotFoundException("Token not found")
                );

        return resetPasswordToken.getExpiryDate().isAfter(LocalDateTime.now()) &&
                resetPasswordToken.getUserId().equals(
                        userRepository.findByEmail(email).orElseThrow(
                                () -> new DataNotFoundException("User with " + email + "do not exist")
                        ).getId()
                );
    }
}
