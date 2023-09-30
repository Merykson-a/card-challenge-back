package com.card.challenge.api.v1.controller;

import com.card.challenge.api.v1.controller.docs.RoundControllerDoc;
import com.card.challenge.api.v1.io.RoundStartRequest;
import com.card.challenge.api.v1.io.RoundStartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rounds")
public class RoundController implements RoundControllerDoc {

    @PostMapping("/start")
    @Override
    public ResponseEntity<RoundStartResponse> start(@RequestBody RoundStartRequest request) {
        return new ResponseEntity<>(new RoundStartResponse(), HttpStatus.OK);
    }
}
