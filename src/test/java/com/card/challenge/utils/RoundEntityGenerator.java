package com.card.challenge.utils;

import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoundEntityGenerator {

    public static RoundEntity generateByReferenceNumber(int referenceNumber) {
        RoundEntity round = new RoundEntity();
        round.setId(referenceNumber);
        round.setPlayers(generatePlayers(round));
        round.setDeckId("DECK" + referenceNumber);
        round.setCreatedAt(LocalDateTime.now());
        return round;
    }

    private static List<PlayerEntity> generatePlayers(RoundEntity round) {
        List<PlayerEntity> players = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            PlayerEntity player = new PlayerEntity();
            player.setId(i);
            player.setName("Name " + i);
            player.setRound(round);
            player.setPlayDate(LocalDateTime.now());
            players.add(player);
        }

        return players;
    }
}
