package com.card.challenge.api.v1.io.deck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalDeckHandResponse {
    private String deck_id;
    private Object piles;
}
