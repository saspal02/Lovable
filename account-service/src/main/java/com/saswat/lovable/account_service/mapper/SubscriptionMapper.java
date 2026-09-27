package com.saswat.lovable.account_service.mapper;

import com.saswat.lovable.account_service.dto.subscription.SubscriptionResponse;
import com.saswat.lovable.account_service.entity.Plan;
import com.saswat.lovable.account_service.entity.Subscription;
import com.saswat.lovable.common_lib.dto.PlanDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanDto toPlanResponse(Plan plan);
}
