package com.card.challenge.api.v1.controller;

import com.card.challenge.api.v1.controller.docs.RoundControllerDoc;
import com.card.challenge.api.v1.io.RoundMapper;
import com.card.challenge.api.v1.io.RoundStartRequest;
import com.card.challenge.api.v1.io.RoundStartResponse;
import com.card.challenge.domain.entity.RoundEntity;
import com.card.challenge.domain.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rounds")
@AllArgsConstructor
public class RoundController implements RoundControllerDoc {

    private final RoundService roundService;
    private final RoundMapper roundMapper;

    @PostMapping("/start")
    @Override
    public ResponseEntity<RoundStartResponse> start(@RequestBody RoundStartRequest request) {
        RoundEntity round = roundService.start(request);
        return new ResponseEntity<>(roundMapper.getRoundStartRespondeByEntity(round), HttpStatus.OK);
    }
}
