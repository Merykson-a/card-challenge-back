package com.card.challenge.domain.service.external_deck;

import com.card.challenge.api.v1.io.deck.DeckHandResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;

public interface ExternalDeckService {
    NewDeckResponse create();

    void drawCards(String roundDeckId, int playerPileKey);

    DeckHandResponse getPlayerHandsByDeckIdAndPlayerId(String deckId, int playerId);
}
