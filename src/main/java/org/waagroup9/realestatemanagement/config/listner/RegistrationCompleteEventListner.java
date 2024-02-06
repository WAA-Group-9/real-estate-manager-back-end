package org.waagroup9.realestatemanagement.config.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.waagroup9.realestatemanagement.config.event.RegistrationCompleteEvent;
import org.waagroup9.realestatemanagement.model.entity.User;
import org.waagroup9.realestatemanagement.service.UserService;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;


    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {

        User user = registrationCompleteEvent.getUser();
        String Token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, Token);
        //Send the email

        String url = registrationCompleteEvent.getAppUrl()
                + "/verifyRegistration?token="
                + Token;
//        sendNewMail(user.getEmail(),"Registration Confirmation","Thank you for registering. Please click on the below link to activate your account."+url);
        //send verification Email
        log.info("Click the link to verify your account: " + url);


    }
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendNewMail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//    }
}