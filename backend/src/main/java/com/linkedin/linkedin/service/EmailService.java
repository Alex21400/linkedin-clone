package com.linkedin.linkedin.service;

import com.linkedin.linkedin.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("from.email")
    private String fromEmail;

    @Value("${confirmation.code.duration}")
    private int durationInMinutes;

    @Async
    @Retryable(
        retryFor = { MessagingException.class, UnsupportedEncodingException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 3000)
    )
    public void sendEmailVerificationCode(String verificationCode, User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromEmail, "LinkedIn");
            helper.setTo(user.getEmail());
            helper.setSubject("Verify Your Email");
            helper.setText(String.format("Only one step to take full advantage of LinkedIn."
                            + " \n\n Enter this code to verify your email: " + " %s. \n\n" + "The code will expire in " + "%s" + " minutes.",
                    verificationCode, durationInMinutes), true);

            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException ex) {
            logger.info("Error while sending verification email: {}", ex.getMessage());
        }
    }

    @Async
    @Retryable(
        retryFor = { MessagingException.class, UnsupportedEncodingException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 3000)
    )
    public void sendPasswordResetToken(String token, User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromEmail, "LinkedIn");
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Your Password");
            helper.setText(String.format("You requested a password reset. If you are not aware of this, please ignore this email."
                            + " \n\n Enter this code to reset your password: " + " %s. \n\n" + "The code will expire in " + "%s" + " minutes.",
                    token, durationInMinutes), true);

            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException ex) {
            logger.info("Error while sending password reset email: {}", ex.getMessage());
        }
    }

    @Recover
    public void handleMessagingException(MessagingException e) {
        logger.error("Max attempts reached. Failed to send email after 3 attempts.");
        logger.error("Error message: {}", e.getMessage());
    }
}
