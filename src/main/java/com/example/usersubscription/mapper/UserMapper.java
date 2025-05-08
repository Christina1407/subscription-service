package com.example.usersubscription.mapper;

import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SubscriptionMapper.class)
public interface UserMapper {
    UserDTO toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    User toEntity(CreateUserRequest request);
}
