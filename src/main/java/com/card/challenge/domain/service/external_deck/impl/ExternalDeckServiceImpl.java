package com.card.challenge.domain.service.external_deck.impl;

import com.card.challenge.api.v1.feign.CardDeckFeignClient;
import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.DeckHandResponse;
import com.card.challenge.api.v1.io.deck.DrawnCardsResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExternalDeckServiceImpl implements ExternalDeckService {

    private final CardDeckFeignClient cardDeckFeignClient;

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
    public DeckHandResponse getPlayerHandsByDeckIdAndPlayerId(String deckId, int playerId) {
        return cardDeckFeignClient.getPlayerHand(deckId, playerId);
    }
}
