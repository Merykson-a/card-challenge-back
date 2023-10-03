package com.card.challenge.domain.service.external_deck.impl;

import com.card.challenge.api.v1.feign.CardDeckFeignClient;
import com.card.challenge.api.v1.io.deck.*;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExternalDeckServiceImpl implements ExternalDeckService {

    private final CardDeckFeignClient cardDeckFeignClient;
    private final ExternalDeckAdapter externalDeckAdapter;

    @Override
    public NewDeckResponse create() {
        return cardDeckFeignClient.create();
    }

    @Override
    public void drawCards(String deckId, int pileKey) {
        cardDeckFeignClient.shuffle(deckId);
        DrawnCardsResponse response = cardDeckFeignClient.drawCards(deckId);
        cardDeckFeignClient.createPlayerHand(deckId, pileKey, getCardValues(response));
    }

    private String getCardValues(DrawnCardsResponse response) {
        return response.getCards().stream().map(CardResponse::getCode).collect(Collectors.joining(","));
    }

    @Override
    public List<CardResponse> getPlayerCardsByDeckIdAndPlayerId(String deckId, int playerId) {
        ExternalDeckHandResponse response = cardDeckFeignClient.getPlayerHand(deckId, playerId);
        return externalDeckAdapter.getCardsByHandAndHandKey(response, playerId);
    }
}
