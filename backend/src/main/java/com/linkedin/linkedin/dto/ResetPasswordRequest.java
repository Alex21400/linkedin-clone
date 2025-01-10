package com.linkedin.linkedin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Please enter password reset token.")
    private String passwordResetToken;

    @NotBlank(message = "Please enter new password.")
    private String newPassword;
}
