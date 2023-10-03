package com.card.challenge.api.v1.io.deck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeckHandCardResponse {
    private String code;
    private String image;
    private Object images;
    private String value;
    private String suit;
}
