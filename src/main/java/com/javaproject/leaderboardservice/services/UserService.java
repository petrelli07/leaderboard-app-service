package com.javaproject.leaderboardservice.services;

import com.javaproject.leaderboardservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Random;

@Component
public class UserService {
    protected int minimum = 10001;
    protected int maximum = 1000001;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String recipient, String subject, String message) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(recipient);

        helper.setSubject(subject);

        // true = text/html
        helper.setText(message, true);

        javaMailSender.send(msg);

    }

    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public long generateVerificationCode(){
        return getRandomNumberUsingInts(minimum, maximum);
    }

    public Boolean sendVerificationMessage(String userEmail) throws MessagingException, IOException {
        String subject = "Email Verification Code";
        long verificationCode = this.generateVerificationCode();
        String message = "<p>Your Verification Code is:"+ verificationCode  +"</p>";
        this.sendEmail(userEmail, subject, message);
        return true;
    }
}
