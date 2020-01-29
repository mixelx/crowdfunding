package com.course.startItProject.service.impl;

import com.course.startItProject.entity.ConfirmationToken;
import com.course.startItProject.entity.User;
import com.course.startItProject.repo.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl {

    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken findByConfirmationToken(String confirmationToken){
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }

    public void sendVerificationToken(User user, EmailSenderServiceImpl emailSenderServiceImpl) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("StartIt!");
        message.setSubject("Verify your account");
        message.setText("To confirm your account, please click here : http://localhost:" +serverPort+ "/confirm-account?token=" + confirmationToken.getConfirmationToken());
        emailSenderServiceImpl.sendEmail(message);
    }

    public String confirmAccount(ConfirmationToken token, UserServiceImpl userServiceImpl) {
        if (token != null) {
            User user = userServiceImpl.findByEmail(token.getUser().getEmail());
            user.setActive(true);
            userServiceImpl.save(user);
            return "login";
        } else {
            return "registration";
        }
    }
}
