package com.example.usersubscription.service;

import com.example.usersubscription.dto.SubscriptionDTO;
import com.example.usersubscription.dto.request.SubscriptionRequest;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO addSubscription(Long userId, SubscriptionRequest request);

    List<SubscriptionDTO> getSubscriptions(Long userId);

    void deleteSubscription(Long userId, Long subId);
}
