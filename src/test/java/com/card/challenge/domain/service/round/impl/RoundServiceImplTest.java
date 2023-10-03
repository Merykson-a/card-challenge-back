package com.card.challenge.domain.service.round.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import com.card.challenge.domain.validation.round.RoundValidation;
import com.card.challenge.utils.CardResponseGenerator;
import com.card.challenge.utils.RoundEntityGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RoundServiceImplTest {

    @InjectMocks
    private RoundServiceImpl roundService;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private RoundValidation roundValidation;

    @Mock
    private ExternalDeckService externalDeckService;

    @Test
    public void verifyIfGetByIdMethodWorksCorrectly() {
        int roundId = 1;
        Mockito.when(roundRepository.findById(any())).thenReturn(Optional.of(new RoundEntity()));

        roundService.getById(roundId);
        Mockito.verify(roundRepository, times(1)).findById(roundId);
    }

    @Test
    public void verifyIfGetByIdMethodThrowsEntityNotFoundException() {
        int roundId = 1;
        Mockito.when(roundRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception =
                Assertions.assertThrows(EntityNotFoundException.class, () -> roundService.getById(roundId));
        Assertions.assertTrue(exception.getMessage().contains("Round.NotFound"));
    }

    @Test
    public void verifyIfStartMethodWorksCorrectly() {
        RoundStartRequest request = new RoundStartRequest();
        request.setPlayerNames(asList("Merykson", "Silva", "Acacio", "Souza"));

        Mockito.when(externalDeckService.create()).thenReturn(new NewDeckResponse());
        
        roundService.start(request);
        Mockito.verify(roundValidation, times(1)).validateForStart(any());
        Mockito.verify(externalDeckService, times(1)).create();
        Mockito.verify(roundRepository, times(1)).save(any());
    }

    @Test
    public void verifyIfFinishMethodWorksCorrectlyWhenIsFinished() {
        RoundEntity round = new RoundEntity();
        round.setFinalizedAt(LocalDateTime.now());
        Mockito.when(roundRepository.findById(1)).thenReturn(Optional.of(round));

        roundService.finish(1);
        Mockito.verify(roundRepository, times(0)).save(any());
    }

    @Test
    public void verifyIfFinishMethodWorksCorrectlyWhenIsNotFinishedAndHasPlayerWithPendingPlay() {
        RoundEntity round = new RoundEntity();
        PlayerEntity player = new PlayerEntity();
        player.setPlayDate(null);
        round.setPlayers(List.of(player));

        Mockito.when(roundRepository.findById(1)).thenReturn(Optional.of(round));

        Exception exception = Assertions.assertThrows(IllegalValueException.class, () -> roundService.finish(1));
        Assertions.assertTrue(exception.getMessage().contains("Round.Result"));
    }

    @Test
    public void verifyIfFinishMethodWorksCorrectlyWhenIsNotFinished() {
        RoundEntity round = RoundEntityGenerator.generateByReferenceNumber(1);
        int firstPlayerId = round.getPlayers().get(0).getId();
        int secondPlayerId = round.getPlayers().get(1).getId();
        String deckId = round.getDeckId();

        List<CardResponse> winningCards = CardResponseGenerator.generateListByReferenceNumber(10);
        List<CardResponse> losingCards = CardResponseGenerator.generateListByReferenceNumber(1);

        Mockito.when(roundRepository.findById(1)).thenReturn(Optional.of(round));
        Mockito.when(externalDeckService.getPlayerCardsByDeckIdAndPlayerId(deckId, firstPlayerId))
               .thenReturn(winningCards);
        Mockito.when(externalDeckService.getPlayerCardsByDeckIdAndPlayerId(deckId, secondPlayerId))
               .thenReturn(losingCards);
        Mockito.when(roundRepository.save(any())).thenReturn(round);

        RoundEntity savedRound = roundService.finish(1);

        assertThat(savedRound.getWinners(), hasSize(1));
        assertThat(savedRound.getWinners().get(0).getPlayer().getId(), equalTo(firstPlayerId));
        Mockito.verify(externalDeckService).getPlayerCardsByDeckIdAndPlayerId(deckId, firstPlayerId);
        Mockito.verify(roundRepository, times(1)).save(any());
    }
}
