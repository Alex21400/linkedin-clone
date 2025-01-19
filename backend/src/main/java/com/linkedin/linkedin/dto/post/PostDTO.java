package com.linkedin.linkedin.dto.post;

import com.linkedin.linkedin.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String content;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
    private Set<UserDTO> likes;
}
