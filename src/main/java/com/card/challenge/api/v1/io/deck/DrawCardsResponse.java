package com.card.challenge.api.v1.io.deck;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DrawCardsResponse {
    private String deck_id;
    private List<CardResponse> cards;
}
