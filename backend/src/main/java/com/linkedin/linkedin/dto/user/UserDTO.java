package com.linkedin.linkedin.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;
    private String company;
    private String position;
    private String location;
    private boolean profileComplete;
}
