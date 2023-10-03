package com.card.challenge.domain.validation.round;

import com.card.challenge.domain.entity.RoundEntity;

public interface RoundValidation {
    void validateForStart(RoundEntity round);
}
