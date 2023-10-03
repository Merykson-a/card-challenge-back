package com.card.challenge.api.v1.io.round;

import com.card.challenge.api.v1.io.round.player.RoundPlayerResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoundStartResponse {
    private int id;
    private LocalDate createdAt = LocalDate.now();
    private List<RoundPlayerResponse> players = new ArrayList<>();
}
