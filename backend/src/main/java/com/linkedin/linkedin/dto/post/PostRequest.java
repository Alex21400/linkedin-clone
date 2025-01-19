package com.linkedin.linkedin.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {
    @NotBlank(message = "Content of the post is required.")
    private String content;
    private String picture;
}
