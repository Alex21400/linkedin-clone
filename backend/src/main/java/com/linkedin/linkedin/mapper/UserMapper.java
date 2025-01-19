package com.linkedin.linkedin.mapper;

import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
