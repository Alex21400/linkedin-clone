package com.linkedin.linkedin.controller;

import com.linkedin.linkedin.dto.*;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.service.AuthenticationService;
import com.linkedin.linkedin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthDTO> signUpUser(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUpUser(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthDTO> signInUser(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signInUser(request));
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/verify-email")
    public ResponseEntity<String> verifyUserEmail(@AuthenticationPrincipal User user, @Valid @RequestBody VerifyEmailRequest request) {
        authenticationService.verifyUserEmail(request.getVerificationCode(), user.getEmail());
        return ResponseEntity.ok().body("Your email has been verified.");
    }

    @GetMapping("/send-email-verification-code")
    public ResponseEntity<String> sendEmailVerificationCode(@AuthenticationPrincipal User user) {
        authenticationService.sendEmailVerificationCode(user.getEmail());
        return ResponseEntity.ok().body("Email verification code sent. Please check Your email.");
    }

    @PutMapping("/send-password-reset-token")
    public ResponseEntity<String> sendPasswordResetToken(@RequestParam String email) {
        authenticationService.sendPasswordResetToken(email);
        return ResponseEntity.ok().body("A password reset token has been sent to Your email.");
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(email, request);
        return ResponseEntity.ok().body("Password reset successfully.");
    }
}
