package com.card.challenge.domain.service.impl;

import com.card.challenge.api.v1.feign.CardDeckFeignClient;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.repository.RoundRepository;
import com.card.challenge.domain.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final CardDeckFeignClient cardDeckFeignClient;

    @Override
    public RoundEntity start(RoundStartRequest request) {
        RoundEntity round = new RoundEntity();
        round.setCreatedAt(LocalDateTime.now());
        round.setPlayers(getPlayersByEntityAndRequest(round, request));

        NewDeckResponse deck = cardDeckFeignClient.create().getBody();
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
