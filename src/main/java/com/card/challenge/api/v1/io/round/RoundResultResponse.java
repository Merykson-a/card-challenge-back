package com.card.challenge.api.v1.io.round;

import com.card.challenge.api.v1.io.round.player.RoundWinnerPlayerResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RoundResultResponse {
    private int roundId;
    private LocalDate finalizedAt;
    private List<RoundWinnerPlayerResponse> winners;
}
