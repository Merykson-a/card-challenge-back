package com.card.challenge.domain.validation.round.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.utils.RoundEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoundValidationImplTest {

    @InjectMocks
    private RoundValidationImpl roundValidation;

    @Test
    public void verifyIfValidateForStartMethodThrowsAnIllegalValueExceptionWhenDeckIdIsNull() {
        RoundEntity round = RoundEntityGenerator.generateByReferenceNumber(1);
        round.setDeckId(null);

        Exception exception =
                Assertions.assertThrows(IllegalValueException.class, () -> roundValidation.validateForStart(round));
        Assertions.assertTrue(exception.getMessage().contains("Round.EmptyDeck"));
    }

    @Test
    public void verifyIfValidateForStartMethodThrowsAnIllegalValueExceptionWhenDeckIdIsEmpty() {
        RoundEntity round = RoundEntityGenerator.generateByReferenceNumber(1);
        round.setDeckId("");

        Exception exception =
                Assertions.assertThrows(IllegalValueException.class, () -> roundValidation.validateForStart(round));
        Assertions.assertTrue(exception.getMessage().contains("Round.EmptyDeck"));
    }

    @Test
    public void verifyIfValidateForStartMethodThrowsAnIllegalValueExceptionWhenThereAreNoFourPlayers() {
        RoundEntity round = RoundEntityGenerator.generateByReferenceNumber(1);
        round.getPlayers().clear();

        Exception exception =
                Assertions.assertThrows(IllegalValueException.class, () -> roundValidation.validateForStart(round));
        Assertions.assertTrue(exception.getMessage().contains("Round.FourPlayers"));
    }

    @Test
    public void verifyIfValidateForStartMethodThrowsAnIllegalValueExceptionWhenHasUnnamedPlayer() {
        RoundEntity round = RoundEntityGenerator.generateByReferenceNumber(1);
        round.getPlayers().get(0).setName("");

        Exception exception =
                Assertions.assertThrows(IllegalValueException.class, () -> roundValidation.validateForStart(round));
        Assertions.assertTrue(exception.getMessage().contains("Round.EmptyPlayerName"));
    }
}
