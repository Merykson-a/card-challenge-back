package com.card.challenge.api.v1.io.deck;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDeckResponse {
    private boolean success;
    private String deck_id;
    private boolean shuffled;
    private int remaining;
}
