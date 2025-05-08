package com.example.usersubscription.service;

import com.example.usersubscription.dto.request.SubscriptionRequest;
import com.example.usersubscription.exception.NotFoundException;
import com.example.usersubscription.mapper.SubscriptionMapper;
import com.example.usersubscription.model.User;
import com.example.usersubscription.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    UserService userService;
    @Mock
    SubscriptionMapper mapper;
    @InjectMocks
    SubscriptionServiceImpl service;

    @Test
    void addSubscription_userNotFound() {
        when(userService.findById(1L)).thenThrow(new NotFoundException(null));
        assertThrows(NotFoundException.class,
                () -> service.addSubscription(1L, new SubscriptionRequest("Netflix")));
    }

    @Test
    void getSubscriptions_callsMapper() {
        User user = User.builder()
                .id(1L)
                .name("Bob")
                .email("b@test.com")
                .subscriptions(Collections.emptyList())
                .build();
        when(userService.findById(1L)).thenReturn(user);

        service.getSubscriptions(1L);

        verify(mapper).toDtoList(Collections.emptyList());
    }
}
