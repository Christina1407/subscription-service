package com.example.usersubscription.service.impl;

import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.request.UpdateUserRequest;
import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.exception.NotFoundException;
import com.example.usersubscription.mapper.UserMapper;
import com.example.usersubscription.model.User;
import com.example.usersubscription.repository.UserRepository;
import com.example.usersubscription.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepo.save(user);
        log.info("Created user {}", saved.getId());
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUser(Long id) {
        return userMapper.toDto(findById(id));
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = findById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        user.setDeletedAt(LocalDateTime.now());
    }

    @Override
    public User findById(Long id) {
        return userRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
