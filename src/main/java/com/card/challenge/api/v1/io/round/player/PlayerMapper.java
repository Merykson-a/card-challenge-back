package com.card.challenge.api.v1.io.round.player;

import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.DeckHandResponse;
import com.card.challenge.api.v1.io.deck.ExternalDeckMapper;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PlayerMapper {

    private final ExternalDeckService externalDeckService;
    private final ExternalDeckMapper externalDeckMapper;

    public PlayerDrawCardResponse getDrawCardResponseByEntity(PlayerEntity player) {
        PlayerDrawCardResponse response = new PlayerDrawCardResponse();
        response.setPlayerId(player.getId());
        response.setRoundId(player.getRound().getId());
        response.setDrawCards(getDrawCards(player));
        return response;
    }

    private List<CardResponse> getDrawCards(PlayerEntity player) {
        String deckId = player.getRound().getDeckId();
        DeckHandResponse deckHands = externalDeckService.getPlayerHandsByDeckIdAndPlayerId(deckId, player.getId());
        return externalDeckMapper.getCardsByHandAndHandKey(deckHands, player.getId());
    }
}
