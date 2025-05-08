package com.example.usersubscription.service;

import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.request.UpdateUserRequest;
import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.model.User;

public interface UserService {
    UserDTO createUser(CreateUserRequest request);

    UserDTO getUser(Long id);

    UserDTO updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    User findById(Long id);
}
