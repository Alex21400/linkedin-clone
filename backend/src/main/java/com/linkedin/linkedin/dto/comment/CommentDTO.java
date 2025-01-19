package com.linkedin.linkedin.dto.comment;

import com.linkedin.linkedin.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
}
