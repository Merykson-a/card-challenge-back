package com.card.challenge.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum SpecialCardValue {
    ACE(1), JACK(11), QUEEN(12), KING(13);

    private final int value;

    public static Optional<SpecialCardValue> get(String innerValue) {
        return EnumSet.allOf(SpecialCardValue.class)
                      .stream()
                      .filter(enumValue -> enumValue.name().equals(innerValue))
                      .findFirst();
    }
}
