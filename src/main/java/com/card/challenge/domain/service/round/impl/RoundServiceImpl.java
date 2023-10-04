package com.card.challenge.domain.service.round.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.entity.RoundWinnerEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import com.card.challenge.domain.service.round.RoundService;
import com.card.challenge.domain.utils.Message;
import com.card.challenge.domain.validation.round.RoundValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final ExternalDeckService externalDeckService;
    private final RoundValidation roundValidation;

    @Override
    public RoundEntity getById(int roundId) {
        Optional<RoundEntity> optional = roundRepository.findById(roundId);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new EntityNotFoundException(Message.toLocale("Round.Error.NotFound"));
    }

    @Override
    public RoundEntity start(RoundStartRequest request) {
        RoundEntity round = new RoundEntity();
        round.setCreatedAt(now());
        round.setPlayers(getPlayersByEntityAndRequest(round, request));

        NewDeckResponse deck = externalDeckService.create();
        round.setDeckId(Objects.requireNonNull(deck).getDeck_id());
        roundValidation.validateForStart(round);
        return roundRepository.save(round);
    }

    private List<PlayerEntity> getPlayersByEntityAndRequest(RoundEntity round, RoundStartRequest request) {
        return request.getPlayerNames().stream().map(name -> {
            PlayerEntity player = new PlayerEntity();
            player.setRound(round);
            player.setName(name);
            return player;
        }).collect(Collectors.toList());
    }

    @Override
    public RoundEntity finish(int roundId) {
        RoundEntity round = getById(roundId);

        if (!round.isFinished()) {
            if (haveAllPlayersPlayed(round.getPlayers())) {
                round.setFinalizedAt(now());
                round.setWinners(getWinnerPlayers(round));
                return roundRepository.save(round);
            }
            throw new IllegalValueException(Message.toLocale("Round.Error.Result"));
        }
        return round;
    }

    private boolean haveAllPlayersPlayed(List<PlayerEntity> players) {
        return players.stream().allMatch(player -> player.getPlayDate() != null);
    }

    private List<RoundWinnerEntity> getWinnerPlayers(RoundEntity entity) {
        List<RoundWinnerEntity> playersResults = getPlayersResults(entity);
        int biggestResult = playersResults.stream().mapToInt(RoundWinnerEntity::getResult).max().orElse(0);
        return playersResults.stream().filter(item -> item.getResult() == biggestResult).collect(Collectors.toList());
    }

    private List<RoundWinnerEntity> getPlayersResults(RoundEntity entity) {
        return entity.getPlayers().stream().map(player -> {
            List<CardResponse> cards = getCards(entity.getDeckId(), player.getId());
            RoundWinnerEntity winner = new RoundWinnerEntity();
            winner.setPlayer(player);
            winner.setRound(entity);
            winner.setResult(getResultSumByCards(cards));
            return winner;
        }).toList();
    }

    private List<CardResponse> getCards(String deckId, int playerId) {
        return externalDeckService.getPlayerCardsByDeckIdAndPlayerId(deckId, playerId);
    }

    private int getResultSumByCards(List<CardResponse> cards) {
        return cards.stream().reduce(0, (result, card) -> result + card.getIntValue(), Integer::sum);
    }
}
