package com.card.challenge.domain.service.player;

import com.card.challenge.domain.entity.PlayerEntity;

public interface PlayerService {
    PlayerEntity getById(int playerId);

    PlayerEntity drawCardsById(int playerId);
}
