package com.example.usersubscription.dto;

import java.time.LocalDateTime;

public record SubscriptionDTO(
        Long id,
        String serviceName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
}
