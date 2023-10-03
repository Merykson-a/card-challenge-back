package com.card.challenge.api.v1.io.round.player;

import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.DeckHandResponse;
import com.card.challenge.api.v1.io.deck.ExternalDeckAdapter;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PlayerMapper {

    private final ExternalDeckService externalDeckService;
    private final ExternalDeckAdapter externalDeckAdapter;

    public PlayerDrawnCardsResponse getDrawnCardsResponseByEntity(PlayerEntity player) {
        PlayerDrawnCardsResponse response = new PlayerDrawnCardsResponse();
        response.setPlayerId(player.getId());
        response.setRoundId(player.getRound().getId());
        response.setDrawnCards(getDrawnCards(player));
        return response;
    }

    private List<CardResponse> getDrawnCards(PlayerEntity player) {
        String deckId = player.getRound().getDeckId();
        DeckHandResponse deckHands = externalDeckService.getPlayerHandsByDeckIdAndPlayerId(deckId, player.getId());
        return externalDeckAdapter.getCardsByHandAndHandKey(deckHands, player.getId());
    }
}
