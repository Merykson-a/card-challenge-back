package com.card.challenge.domain.service;

import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.RoundEntity;

public interface RoundService {
    RoundEntity start(RoundStartRequest request);
}
