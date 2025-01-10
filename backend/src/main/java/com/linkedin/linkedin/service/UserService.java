package com.linkedin.linkedin.service;

import com.linkedin.linkedin.exception.ResourceNotFoundException;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
