package com.example.usersubscription.service.impl;

import com.example.usersubscription.dto.TopSubscriptionDTO;
import com.example.usersubscription.repository.SubscriptionStatsRepository;
import com.example.usersubscription.service.SubscriptionStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionStatsServiceImpl implements SubscriptionStatsService {

    private final SubscriptionStatsRepository repo;

    @Override
    public List<TopSubscriptionDTO> top(int limit) {
        return repo.findTop(PageRequest.of(0, limit));
    }
}