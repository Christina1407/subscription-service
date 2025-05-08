package com.example.usersubscription.mapper;

import com.example.usersubscription.dto.SubscriptionDTO;
import com.example.usersubscription.model.Subscription;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDTO toDto(Subscription entity);

    List<SubscriptionDTO> toDtoList(List<Subscription> entities);
}
