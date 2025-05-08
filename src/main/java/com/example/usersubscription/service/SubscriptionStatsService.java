package com.example.usersubscription.service;

import com.example.usersubscription.dto.TopSubscriptionDTO;

import java.util.List;

public interface SubscriptionStatsService {
    List<TopSubscriptionDTO> top(int limit);
}
