package com.card.challenge.domain.service.player.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.repository.PlayerRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ExternalDeckService externalDeckService;

    @Test
    public void verifyIfGetByIdMethodWorksCorrectly() {
        int playerId = 1;
        Mockito.when(playerRepository.findById(any())).thenReturn(Optional.of(new PlayerEntity()));

        playerService.getById(playerId);
        Mockito.verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    public void verifyIfGetByIdMethodThrowsEntityNotFoundException() {
        int playerId = 1;
        Mockito.when(playerRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception =
                Assertions.assertThrows(EntityNotFoundException.class, () -> playerService.getById(playerId));
        Assertions.assertTrue(exception.getMessage().contains("Player.Error.NotFound"));
    }

    @Test
    public void verifyIfDrawCardsByIdMethodWorksCorrectly() {
        PlayerEntity player = new PlayerEntity();
        player.setId(1);
        player.setPlayDate(null);

        RoundEntity round = new RoundEntity();
        round.setDeckId("DECK1");
        player.setRound(round);

        Mockito.when(playerRepository.findById(any())).thenReturn(Optional.of(player));

        playerService.drawCardsById(player.getId());
        Mockito.verify(playerRepository, times(1)).findById(player.getId());
        Mockito.verify(externalDeckService, times(1)).drawCards(round.getDeckId(), player.getId());
    }

    @Test
    public void verifyIfDrawCardsByIdMethodThrowsIllegalValueExceptionWhenHasAlreadyPlayed() {
        PlayerEntity player = new PlayerEntity();
        player.setId(1);
        player.setPlayDate(LocalDateTime.now());

        RoundEntity round = new RoundEntity();
        round.setDeckId("DECK1");
        player.setRound(round);

        Mockito.when(playerRepository.findById(any())).thenReturn(Optional.of(player));

        Exception exception =
                Assertions.assertThrows(IllegalValueException.class, () -> playerService.drawCardsById(player.getId()));
        Assertions.assertTrue(exception.getMessage().contains("Player.Error.PlayAlreadyMade"));

        Mockito.verify(playerRepository, times(1)).findById(player.getId());
        Mockito.verify(externalDeckService, times(0)).drawCards(round.getDeckId(), player.getId());
    }
}
