package com.card.challenge.domain.service.round.impl;

import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import com.card.challenge.domain.service.round.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final ExternalDeckService externalDeckService;

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
}
