package com.card.challenge.api.v1.io.deck;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardResponse {
    private String code;
    private String image;
    private String value;
}
