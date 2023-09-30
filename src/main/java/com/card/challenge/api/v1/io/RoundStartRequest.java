package com.card.challenge.api.v1.io;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoundStartRequest {

    @ApiModelProperty(example = "[\"Player 1\", \"Player 2\", \"Player 3\", \"Player 4\"]")
    private List<String> playerNames;
}
