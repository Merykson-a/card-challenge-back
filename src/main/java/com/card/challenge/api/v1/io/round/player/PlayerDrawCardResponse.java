package com.card.challenge.api.v1.io.round.player;

import com.card.challenge.api.v1.io.deck.CardResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerDrawCardResponse {
    private int roundId;
    private int playerId;
    private List<CardResponse> drawCards;
}
