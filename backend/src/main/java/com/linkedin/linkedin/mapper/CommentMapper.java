package com.linkedin.linkedin.mapper;

import com.linkedin.linkedin.dto.comment.CommentDTO;
import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.model.Comment;
import com.linkedin.linkedin.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDTO(Comment comment);
    Comment toEntity(CommentDTO commentDTO);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    List<CommentDTO> toDTOS(List<Comment> comments);
    List<Comment> toEntities(List<CommentDTO> commentDTOS);
}
