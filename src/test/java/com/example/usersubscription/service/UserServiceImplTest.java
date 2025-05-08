package com.example.usersubscription.service;

import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.exception.NotFoundException;
import com.example.usersubscription.mapper.UserMapper;
import com.example.usersubscription.model.User;
import com.example.usersubscription.repository.UserRepository;
import com.example.usersubscription.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repo;
    @Mock
    UserMapper mapper;
    @InjectMocks
    UserServiceImpl service;

    @Test
    void getUser_notFound_throws() {
        when(repo.findByIdAndDeletedAtIsNull(42L)).thenThrow(new NotFoundException(null));
        assertThrows(NotFoundException.class, () -> service.getUser(42L));
    }

    @Test
    void createUser_savesAndReturnsDTO() {
        CreateUserRequest request = new CreateUserRequest("Alice", "a@test.com");
        User entity = User.builder().name("Alice").email("a@test.com").build();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(new UserDTO(1L, "testName", "testEmail",
                LocalDateTime.now(), LocalDateTime.now(), null, null));

        UserDTO dto = service.createUser(request);

        assertNotNull(dto);
        verify(repo).save(entity);
    }
}
