package com.card.challenge.api.v1.io.deck;

import com.card.challenge.domain.enums.SpecialCardValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

import static java.lang.Integer.parseInt;

@Getter
@Setter
@Builder
public class CardResponse {
    private String code;
    private String image;
    private String value;

    public int getIntValue() {
        Optional<SpecialCardValue> enumValue = SpecialCardValue.get(value);
        return enumValue.map(SpecialCardValue::getValue).orElseGet(() -> parseInt(value));
    }
}
