package com.card.challenge.api.v1.io;

import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        return players.stream().map(player -> {
            RoundPlayerResponse response = new RoundPlayerResponse();
            response.setId(player.getId());
            response.setName(player.getName());
            return response;
        }).collect(Collectors.toList());
    }
}
