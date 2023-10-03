package com.card.challenge.domain.service.external_deck;

import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;

import java.util.List;

public interface ExternalDeckService {
    NewDeckResponse create();

    void drawCards(String roundDeckId, int playerPileKey);

    List<CardResponse> getPlayerCardsByDeckIdAndPlayerId(String deckId, int playerId);
}
