package com.linkedin.linkedin.service;

import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.dto.post.PostDTO;
import com.linkedin.linkedin.dto.post.PostRequest;
import com.linkedin.linkedin.exception.ResourceNotFoundException;
import com.linkedin.linkedin.mapper.PostMapper;
import com.linkedin.linkedin.mapper.UserMapper;
import com.linkedin.linkedin.model.Post;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.repository.PostRepository;
import com.linkedin.linkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public List<PostDTO> getFeedPosts(Long userId) {
        return postMapper.toDTOs(postRepository.findByUserIdNotOrderByCreatedAtDesc(userId));
    }

    public List<PostDTO> getAllPosts() {
        return postMapper.toDTOs(postRepository.findALlByOrderByCreatedAtDesc());
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        return postMapper.toDTO(post);
    }

    public PostDTO createPost(Long userId, PostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Post post = new Post();
        post.setContent(request.getContent());
        post.setPicture(request.getPicture());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return postMapper.toDTO(savedPost);
    }

    public PostDTO updatePost(Long userId, Long postId, PostRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        if(!post.getUser().equals(user)) {
            throw new AccessDeniedException("You are not author of this post.");
        }

        if(request.getContent() != null) post.setContent(request.getContent());
        if(request.getPicture() != null) post.setPicture(request.getPicture());
        Post updatedPost = postRepository.save(post);
        return postMapper.toDTO(updatedPost);
    }

    public void deletePost(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        if(!post.getUser().equals(user)) {
            throw new AccessDeniedException("You can only delete your own posts.");
        }

        postRepository.delete(post);
    }

    public PostDTO likePost(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        if(post.getLikes().contains(user)) {
            post.getLikes().remove(user);
        } else {
            post.getLikes().add(user);
        }

        Post updatedPost = postRepository.save(post);
        return postMapper.toDTO(updatedPost);
    }

    public Set<UserDTO> getPostLikes(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found."));

        return post.getLikes().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
