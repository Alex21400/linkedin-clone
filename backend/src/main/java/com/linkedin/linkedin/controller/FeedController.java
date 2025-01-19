package com.linkedin.linkedin.controller;

import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.dto.comment.CommentDTO;
import com.linkedin.linkedin.dto.comment.CommentRequest;
import com.linkedin.linkedin.dto.post.PostDTO;
import com.linkedin.linkedin.dto.post.PostRequest;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.service.CommentService;
import com.linkedin.linkedin.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/feed")
@RequiredArgsConstructor
public class FeedController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFeedPosts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.getFeedPosts(user.getId()));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal User user
    ) {
        Long userId = user.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId, request));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> createPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal User user
    ) {
        Long userId = user.getId();
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(userId, postId, request));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        postService.deletePost(postId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.likePost(postId, user.getId()));
    }

    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<Set<UserDTO>> getPostLikes(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPostLikes(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CommentRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, user.getId(), request));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getPostComments(@PathVariable Long postId) {
        return ResponseEntity.ok().body(commentService.getPostComments(postId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> editComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok().body(commentService.editComment(commentId, user.getId(), request));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user
    ) {
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
