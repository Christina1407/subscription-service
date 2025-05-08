package com.example.usersubscription.controller;

import com.example.usersubscription.dto.TopSubscriptionDTO;
import com.example.usersubscription.service.SubscriptionStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionStatsController {

    private final SubscriptionStatsService stats;

    @GetMapping("/top")
    public List<TopSubscriptionDTO> top(@RequestParam(defaultValue = "3") int limit) {
        return stats.top(limit);
    }
}
