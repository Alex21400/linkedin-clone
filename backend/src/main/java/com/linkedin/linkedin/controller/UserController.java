package com.linkedin.linkedin.controller;

import com.linkedin.linkedin.dto.user.UpdateUserRequest;
import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile/{userId}")
    public UserDTO updateUsersProfile(
            @AuthenticationPrincipal User user,
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
            ) {
        if(!user.getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update another user's profile.");
        }

        return userService.updateUsersProfile(userId, request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @AuthenticationPrincipal User user) {
        if(!user.getId().equals(userId)) {
            throw new AccessDeniedException("You can only delete your own profile.");
        }

        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }
}
