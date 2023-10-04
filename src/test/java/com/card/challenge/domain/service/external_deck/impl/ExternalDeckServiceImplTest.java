package com.card.challenge.domain.service.external_deck.impl;

import com.card.challenge.api.v1.feign.CardDeckFeignClient;
import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.api.v1.io.deck.DrawnCardsResponse;
import com.card.challenge.api.v1.io.deck.ExternalDeckAdapter;
import com.card.challenge.api.v1.io.deck.ExternalDeckHandResponse;
import com.card.challenge.utils.CardResponseGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ExternalDeckServiceImplTest {

    @InjectMocks
    private ExternalDeckServiceImpl externalDeckService;

    @Mock
    private CardDeckFeignClient cardDeckFeignClient;

    @Mock
    private ExternalDeckAdapter externalDeckAdapter;

    @Test
    public void verifyIfCreateMethodWorksCorrectly() {
        externalDeckService.create();
        Mockito.verify(cardDeckFeignClient, times(1)).create();
    }

    @Test
    public void verifyIfDrawCardsMethodWorksCorrectly() {
        String deckId = "DECK1";
        DrawnCardsResponse response = new DrawnCardsResponse();
        response.setDeck_id(deckId);
        response.setCards(CardResponseGenerator.generateListByReferenceNumber(1));

        Mockito.when(cardDeckFeignClient.drawCards(deckId)).thenReturn(response);

        externalDeckService.drawCards(deckId, 1);

        Mockito.verify(cardDeckFeignClient, times(1)).shuffle(deckId);
        Mockito.verify(cardDeckFeignClient, times(1)).drawCards(deckId);
        Mockito.verify(cardDeckFeignClient, times(1)).createPlayerHand(deckId, 1, getCardValues(response));
    }

    private String getCardValues(DrawnCardsResponse response) {
        return response.getCards().stream().map(CardResponse::getCode).collect(Collectors.joining(","));
    }

    @Test
    public void verifyIfGetPlayerCardsByDeckIdAndPlayerIdMethodWorksCorrectly() {
        String deckId = "DECK1";
        ExternalDeckHandResponse response = new ExternalDeckHandResponse();
        response.setDeck_id(deckId);
        response.setPiles(mock(Object.class));

        Mockito.when(cardDeckFeignClient.getPlayerHand(deckId, 1)).thenReturn(response);

        externalDeckService.getPlayerCardsByDeckIdAndPlayerId(deckId, 1);

        Mockito.verify(cardDeckFeignClient, times(1)).getPlayerHand(deckId, 1);
        Mockito.verify(externalDeckAdapter, times(1)).getCardsByHandAndHandKey(response, 1);
    }
}
