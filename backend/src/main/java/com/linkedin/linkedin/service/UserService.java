package com.linkedin.linkedin.service;

import com.linkedin.linkedin.dto.user.UpdateUserRequest;
import com.linkedin.linkedin.dto.user.UserDTO;
import com.linkedin.linkedin.exception.ResourceNotFoundException;
import com.linkedin.linkedin.mapper.UserMapper;
import com.linkedin.linkedin.model.User;
import com.linkedin.linkedin.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDTO updateUsersProfile(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if(request.getLastName() != null) user.setLastName(request.getLastName());
        if(request.getCompany() != null) user.setCompany(request.getCompany());
        if(request.getPosition() != null) user.setPosition(request.getPosition());
        if(request.getLocation() != null) user.setLocation(request.getLocation());

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);

        if(user != null) {
            entityManager.createNativeQuery("DELETE FROM posts_likes WHERE user_id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            entityManager.remove(user);
        }
    }
}
