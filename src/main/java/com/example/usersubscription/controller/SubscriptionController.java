package com.example.usersubscription.controller;

import com.example.usersubscription.dto.SubscriptionDTO;
import com.example.usersubscription.dto.request.SubscriptionRequest;
import com.example.usersubscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subService;

    @PostMapping
    @ResponseStatus(CREATED)
    public SubscriptionDTO add(@PathVariable Long userId, @Valid @RequestBody SubscriptionRequest request) {
        return subService.addSubscription(userId, request);
    }

    @GetMapping
    public List<SubscriptionDTO> get(@PathVariable Long userId) {
        return subService.getSubscriptions(userId);
    }

    @DeleteMapping("/{subId}")
    @ResponseStatus(NO_CONTENT)
    public void remove(@PathVariable Long userId, @PathVariable Long subId) {
        subService.deleteSubscription(userId, subId);
    }
}
