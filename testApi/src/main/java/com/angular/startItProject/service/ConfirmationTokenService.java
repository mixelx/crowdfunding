package com.angular.startItProject.service;

import com.angular.startItProject.entity.ConfirmationToken;
import com.angular.startItProject.entity.User;
import com.angular.startItProject.repo.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken findByConfirmationToken(String confirmationToken){
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }

    public void sendVerificationToken(User user, EmailSenderService emailSenderService) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("StartIt!");
        message.setSubject("Verify your account");
        message.setText("To confirm your account, please click here : http://localhost:" +serverPort+ "/confirm-account?token=" + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(message);
    }

    public String confirmAccount(ConfirmationToken token, UserService userService) {
        if (token != null) {
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setActive(true);
            userService.save(user);
            return "login";
        } else {
            return "registration";
        }
    }
}
