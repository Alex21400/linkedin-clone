package com.linkedin.linkedin.mapper;

import com.linkedin.linkedin.dto.comment.CommentDTO;
import com.linkedin.linkedin.dto.post.PostDTO;
import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.model.Comment;
import com.linkedin.linkedin.model.Post;
import com.linkedin.linkedin.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDTO(Post post);
    Post toEntity(PostDTO postDTO);

    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);

    CommentDTO toDTO(Comment comment);
    Comment toEntity(CommentDTO commentDTO);

    List<PostDTO> toDTOs(List<Post> posts);
    List<Post> toEntities(List<PostDTO> postDTOs);
}
