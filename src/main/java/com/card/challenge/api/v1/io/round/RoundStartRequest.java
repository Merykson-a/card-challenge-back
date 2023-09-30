package com.card.challenge.api.v1.io.round;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoundStartRequest {

    @Schema(example = "[\"Player 1\", \"Player 2\", \"Player 3\", \"Player 4\"]")
    private List<String> playerNames;
}
