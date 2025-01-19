package com.linkedin.linkedin.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank(message = "Content of the comment is required.")
    private String content;
}
