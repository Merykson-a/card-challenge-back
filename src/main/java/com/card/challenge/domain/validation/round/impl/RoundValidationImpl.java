package com.card.challenge.domain.validation.round.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.utils.Message;
import com.card.challenge.domain.validation.round.RoundValidation;
import org.springframework.stereotype.Component;

@Component
public class RoundValidationImpl implements RoundValidation {

    @Override
    public void validateForStart(RoundEntity round) {
        validateDeckId(round);
        validatePlayerNumbers(round);
        validatePlayerNames(round);
    }

    private void validateDeckId(RoundEntity round) {
        if (round.getDeckId() == null || round.getDeckId().trim().isEmpty()) {
            throw new IllegalValueException(Message.toLocale("Round.EmptyDeck"));
        }
    }

    private void validatePlayerNumbers(RoundEntity round) {
        if (round.getPlayers().size() != 4) {
            throw new IllegalValueException(Message.toLocale("Round.FourPlayers"));
        }
    }

    private void validatePlayerNames(RoundEntity round) {
        if (round.getPlayers()
                 .stream()
                 .anyMatch(player -> player.getName() == null || player.getName().trim().isEmpty())) {
            throw new IllegalValueException(Message.toLocale("Round.EmptyPlayerName"));
        }
    }
}
