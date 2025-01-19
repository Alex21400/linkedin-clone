package com.linkedin.linkedin.service;

import com.linkedin.linkedin.dto.auth.AuthDTO;
import com.linkedin.linkedin.dto.auth.SignInRequest;
import com.linkedin.linkedin.dto.auth.SignUpRequest;
import com.linkedin.linkedin.dto.auth.ResetPasswordRequest;
import com.linkedin.linkedin.exception.EmailAlreadyTakenException;
import com.linkedin.linkedin.exception.EmailVerificationException;
import com.linkedin.linkedin.exception.PasswordResetException;
import com.linkedin.linkedin.model.Role;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Value("${confirmation.code.duration}")
    private int durationInMinutes;

    public AuthDTO signUpUser(SignUpRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()) {
            throw new EmailAlreadyTakenException("Email is already in use.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        String emailVerificationCode = generateRandomToken();
        user.setEmailVerificationCode(encoder.encode(emailVerificationCode));
        user.setEmailVerificationCodeExpiration(LocalDateTime.now().plusMinutes(durationInMinutes));
        userRepository.save(user);

        emailService.sendEmailVerificationCode(emailVerificationCode,user);

        String token = jwtService.generateToken(user);

        return new AuthDTO(token);
    }

    public AuthDTO signInUser(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Incorrect email or password."));

        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect email or password.");
        }

        String token = jwtService.generateToken(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        user.getAuthorities()
                )
        );

        return new AuthDTO(token);
    }

    public void verifyUserEmail(String verificationCode, String email) {
        User user = userService.getUserByEmail(email);

        if(user.isEmailVerified()) {
            throw new IllegalArgumentException("Your email is already verified.");
        }

        if(!encoder.matches(verificationCode, user.getEmailVerificationCode())) {
            throw new EmailVerificationException("Email verification code is invalid.");
        }

        if(user.getEmailVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new EmailVerificationException("Email verification code has expired.");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationCode(null);
        user.setEmailVerificationCodeExpiration(null);

        userRepository.save(user);
    }

    public void sendEmailVerificationCode(String email) {
        User user = userService.getUserByEmail(email);

        String emailVerificationCode = generateRandomToken();
        user.setEmailVerificationCode(encoder.encode(emailVerificationCode));
        user.setEmailVerificationCodeExpiration(LocalDateTime.now().plusMinutes(durationInMinutes));
        userRepository.save(user);

        emailService.sendEmailVerificationCode(emailVerificationCode,user);
    }

    public void sendPasswordResetToken(String email) {
        User user = userService.getUserByEmail(email);

        String passwordResetToken = generateRandomToken();
        user.setPasswordResetToken(encoder.encode(passwordResetToken));
        user.setPasswordResetTokenExpiration(LocalDateTime.now().plusMinutes(durationInMinutes));
        userRepository.save(user);

        emailService.sendPasswordResetToken(passwordResetToken, user);
    }

    public void resetPassword(String email, ResetPasswordRequest request) {
        User user = userService.getUserByEmail(email);

        if(!encoder.matches(request.getPasswordResetToken(), user.getPasswordResetToken())) {
            throw new PasswordResetException("The token you have provided is invalid.");
        }

        if(user.getPasswordResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new PasswordResetException("The password reset token has expired.");
        }

        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiration(null);
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private static String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(5);

        for(int i = 0; i < 6; i++) {
            token.append(random.nextInt(10));
        }

        return token.toString();
    }
}
