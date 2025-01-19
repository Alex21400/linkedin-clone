package com.linkedin.linkedin.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailRequest {
    @NotBlank(message = "Please enter a verification code.")
    private String verificationCode;
}
