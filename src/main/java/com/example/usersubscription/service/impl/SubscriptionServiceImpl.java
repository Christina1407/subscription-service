package com.example.usersubscription.service.impl;

import com.example.usersubscription.dto.SubscriptionDTO;
import com.example.usersubscription.dto.request.SubscriptionRequest;
import com.example.usersubscription.exception.NotFoundException;
import com.example.usersubscription.mapper.SubscriptionMapper;
import com.example.usersubscription.model.Subscription;
import com.example.usersubscription.model.User;
import com.example.usersubscription.repository.SubscriptionRepository;
import com.example.usersubscription.service.SubscriptionService;
import com.example.usersubscription.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserService userService;
    private final SubscriptionRepository subRepo;
    private final SubscriptionMapper mapper;

    @Override
    public SubscriptionDTO addSubscription(Long userId, SubscriptionRequest req) {
        User user = userService.findById(userId);
        Subscription sub = Subscription.builder()
                .serviceName(req.getServiceName())
                .user(user).build();
        Subscription saved = subRepo.save(sub);
        log.info("Created subscription {}", saved.getId());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getSubscriptions(Long userId) {
        User user = userService.findById(userId);
        return mapper.toDtoList(user.getSubscriptions().stream()
                .filter(sub -> Objects.isNull(sub.getDeletedAt()))
                .toList());
    }

    @Override
    public void deleteSubscription(Long userId, Long subId) {
        Subscription sub = subRepo.findById(subId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (!sub.getUser().getId().equals(userId)) {
            throw new NotFoundException("Subscription not found for user");
        }
        sub.setDeletedAt(LocalDateTime.now());
    }
}
