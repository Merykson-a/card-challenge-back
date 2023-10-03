package com.card.challenge.domain.service.round.impl;

import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.entity.RoundWinnerEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import com.card.challenge.domain.service.round.RoundService;
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

    @Override
    public RoundEntity getById(int roundId) {
        Optional<RoundEntity> optional = roundRepository.findById(roundId);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new EntityNotFoundException("Partida não encontrada");
    }

    @Override
    public RoundEntity start(RoundStartRequest request) {
        RoundEntity round = new RoundEntity();

        round.setCreatedAt(now());
        round.setPlayers(getPlayersByEntityAndRequest(round, request));

        NewDeckResponse deck = externalDeckService.create();
        round.setDeckId(Objects.requireNonNull(deck).getDeck_id());
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
        RoundEntity entity = getById(roundId);
        if (entity.getFinalizedAt() == null) {
            if (hasPlayerWithoutPlayDate(entity.getPlayers())) {
                throw new RuntimeException("Ainda possui jogadores que não puxaram cartas");
            }
            else {
                entity.setFinalizedAt(now());
                entity.setWinners(getWinnersByEntity(entity));
                return roundRepository.save(entity);
            }
        }
        return entity;
    }

    private boolean hasPlayerWithoutPlayDate(List<PlayerEntity> players) {
        return players.stream().anyMatch(player -> player.getPlayDate() == null);
    }

    private List<RoundWinnerEntity> getWinnersByEntity(RoundEntity entity) {
        List<RoundWinnerEntity> playerResults = getPlayerResults(entity);
        int biggestResult = playerResults.stream().mapToInt(RoundWinnerEntity::getResult).max().orElse(0);
        return playerResults.stream().filter(item -> item.getResult() == biggestResult).collect(Collectors.toList());
    }

    private List<RoundWinnerEntity> getPlayerResults(RoundEntity entity) {
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
