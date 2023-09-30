package com.card.challenge.domain.service.impl;

import com.card.challenge.api.v1.io.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;

    @Override
    public RoundEntity start(RoundStartRequest request) {
        RoundEntity round = new RoundEntity();
        round.setCreatedAt(OffsetDateTime.now());
        round.setPlayers(getPlayersByEntityAndRequest(round, request));
        round.setDeckId("deckId1234");
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
