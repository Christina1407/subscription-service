package com.example.usersubscription.repository;

import com.example.usersubscription.dto.TopSubscriptionDTO;
import com.example.usersubscription.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionStatsRepository extends JpaRepository<Subscription, Long> {

    @Query("""
            SELECT new com.example.usersubscription.dto.TopSubscriptionDTO(s.serviceName, COUNT(s))
            FROM   Subscription s
            WHERE  s.deletedAt IS NULL
            GROUP  BY s.serviceName
            ORDER  BY COUNT(s) DESC
            """)
    List<TopSubscriptionDTO> findTop(Pageable page);
}