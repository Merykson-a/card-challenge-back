package com.card.challenge.api.v1.controller;

import com.card.challenge.api.v1.controller.docs.RoundControllerDoc;
import com.card.challenge.api.v1.io.round.RoundMapper;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.api.v1.io.round.RoundStartResponse;
import com.card.challenge.api.v1.io.round.player.PlayerDrawnCardsResponse;
import com.card.challenge.api.v1.io.round.player.PlayerMapper;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.service.player.PlayerService;
import com.card.challenge.domain.service.round.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rounds")
@AllArgsConstructor
public class RoundController implements RoundControllerDoc {

    private final RoundService roundService;
    private final RoundMapper roundMapper;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @PostMapping("/start")
    @Override
    public ResponseEntity<RoundStartResponse> start(@RequestBody RoundStartRequest request) {
        RoundEntity round = roundService.start(request);
        return new ResponseEntity<>(roundMapper.getRoundStartRespondeByEntity(round), HttpStatus.OK);
    }

    @PostMapping("/players/{playerId}/cards/draw")
    @Override
    public ResponseEntity<PlayerDrawnCardsResponse> drawCards(@PathVariable int playerId) {
        PlayerEntity player = playerService.drawCardsById(playerId);
        return new ResponseEntity<>(playerMapper.getDrawnCardsResponseByEntity(player), HttpStatus.OK);
    }
}
