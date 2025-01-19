package com.linkedin.linkedin.service;

import com.linkedin.linkedin.dto.comment.CommentDTO;
import com.linkedin.linkedin.dto.comment.CommentRequest;
import com.linkedin.linkedin.exception.ResourceNotFoundException;
import com.linkedin.linkedin.mapper.CommentMapper;
import com.linkedin.linkedin.model.Comment;
import com.linkedin.linkedin.model.Post;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.repository.CommentRepository;
import com.linkedin.linkedin.repository.PostRepository;
import com.linkedin.linkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentDTO createComment(Long postId, Long userId, CommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDTO(savedComment);
    }

    public List<CommentDTO> getPostComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        return commentMapper.toDTOS(post.getComments());
    }

    public CommentDTO editComment(Long commentId, Long userId, CommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        if(!comment.getUser().equals(user)) {
            throw new AccessDeniedException("You can only update your own comments.");
        }

        comment.setContent(request.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDTO(updatedComment);
    }

    public void deleteComment(Long commentId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        if(!comment.getUser().equals(user)) {
            throw new AccessDeniedException("You can only delete your own comments.");
        }

        commentRepository.delete(comment);
    }
}
