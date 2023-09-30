package com.card.challenge.api.v1.feign;

import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "card-deck", url = "https://deckofcardsapi.com", path = "/api/deck")
public interface CardDeckFeignClient {

    @GetMapping("/new")
    public ResponseEntity<NewDeckResponse> create();
}
