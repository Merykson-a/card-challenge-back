package com.card.challenge.domain.api.controller;

import com.card.challenge.api.v1.controller.RoundController;
import com.card.challenge.api.v1.io.round.RoundMapper;
import com.card.challenge.api.v1.io.round.RoundResultResponse;
import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.api.v1.io.round.RoundStartResponse;
import com.card.challenge.api.v1.io.round.player.PlayerDrawnCardsResponse;
import com.card.challenge.api.v1.io.round.player.PlayerMapper;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.service.player.PlayerService;
import com.card.challenge.domain.service.round.RoundService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RoundControllerTest {

    @InjectMocks
    private RoundController roundController;

    @Mock
    private RoundService roundService;

    @Mock
    private RoundMapper roundMapper;

    @Mock
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;

    @Test
    public void verifyIfStartMethodWorksCorrectly() {
        RoundStartRequest request = new RoundStartRequest();
        RoundEntity roundEntity = new RoundEntity();
        RoundStartResponse response = new RoundStartResponse();

        Mockito.when(roundService.start(request)).thenReturn(roundEntity);
        Mockito.when(roundMapper.getRoundStartRespondeByEntity(roundEntity)).thenReturn(response);

        ResponseEntity<RoundStartResponse> result = roundController.start(request);

        Mockito.verify(roundService, times(1)).start(request);
        Mockito.verify(roundMapper, times(1)).getRoundStartRespondeByEntity(roundEntity);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    public void verifyIfDrawCardsMethodWorksCorrectly() {
        int playerId = 1;
        PlayerEntity playerEntity = new PlayerEntity();
        PlayerDrawnCardsResponse response = new PlayerDrawnCardsResponse();

        Mockito.when(playerService.drawCardsById(playerId)).thenReturn(playerEntity);
        Mockito.when(playerMapper.getDrawnCardsResponseByEntity(playerEntity)).thenReturn(response);

        ResponseEntity<PlayerDrawnCardsResponse> result = roundController.drawCards(playerId);

        Mockito.verify(playerService, times(1)).drawCardsById(playerId);
        Mockito.verify(playerMapper, times(1)).getDrawnCardsResponseByEntity(playerEntity);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    public void verifyIfResultMethodWorksCorrectly() {
        int roundId = 1;
        RoundEntity roundEntity = new RoundEntity();
        RoundResultResponse response = new RoundResultResponse();

        Mockito.when(roundService.finish(roundId)).thenReturn(roundEntity);
        Mockito.when(roundMapper.getRoundResultResponseByEntity(roundEntity)).thenReturn(response);

        ResponseEntity<RoundResultResponse> result = roundController.result(roundId);

        Mockito.verify(roundService, times(1)).finish(roundId);
        Mockito.verify(roundMapper, times(1)).getRoundResultResponseByEntity(roundEntity);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }
}




