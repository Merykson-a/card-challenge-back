package com.card.challenge.utils;

import com.card.challenge.api.v1.io.deck.CardResponse;
import com.card.challenge.domain.enums.SpecialCardValue;

import java.util.ArrayList;
import java.util.List;

public class CardResponseGenerator {

    public static List<CardResponse> generateListByReferenceNumber(int referenceNumber) {
        List<CardResponse> cards = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            cards.add(CardResponse.builder().value(String.valueOf(i + referenceNumber)).build());
        }
        cards.add(CardResponse.builder().value(String.valueOf(SpecialCardValue.KING)).build());
        return cards;
    }
}
