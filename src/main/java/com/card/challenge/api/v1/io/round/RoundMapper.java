package com.card.challenge.api.v1.io.round;

import com.card.challenge.api.v1.io.round.player.RoundPlayerResponse;
import com.card.challenge.api.v1.io.round.player.RoundWinnerPlayerResponse;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.entity.RoundWinnerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RoundMapper {

    public RoundStartResponse getRoundStartRespondeByEntity(RoundEntity entity) {
        RoundStartResponse response = new RoundStartResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt().toLocalDate());
        response.setPlayers(getPlayersByEntities(entity.getPlayers()));
        return response;
    }

    private List<RoundPlayerResponse> getPlayersByEntities(List<PlayerEntity> players) {
        return players.stream()
                      .map(player -> new RoundPlayerResponse(player.getId(), player.getName()))
                      .collect(toList());
    }

    public RoundResultResponse getRoundResultResponseByEntity(RoundEntity round) {
        RoundResultResponse response = new RoundResultResponse();
        response.setRoundId(round.getId());
        response.setFinalizedAt(round.getFinalizedAt().toLocalDate());
        response.setWinners(getWinners(round.getWinners()));
        return response;
    }

    private List<RoundWinnerPlayerResponse> getWinners(List<RoundWinnerEntity> winners) {
        return winners.stream().map(winner -> {
            RoundWinnerPlayerResponse response = new RoundWinnerPlayerResponse();
            response.setId(winner.getId());
            response.setName(winner.getPlayer().getName());
            response.setResult(winner.getResult());
            return response;
        }).collect(toList());
    }
}
