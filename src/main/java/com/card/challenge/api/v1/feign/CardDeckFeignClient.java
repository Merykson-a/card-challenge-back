package com.card.challenge.api.v1.feign;

import com.card.challenge.api.v1.io.deck.DeckHandResponse;
import com.card.challenge.api.v1.io.deck.DrawCardsResponse;
import com.card.challenge.api.v1.io.deck.NewDeckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-deck", url = "https://deckofcardsapi.com", path = "/api/deck")
public interface CardDeckFeignClient {

    @PostMapping("/new")
    NewDeckResponse create();

    @GetMapping("/{deckId}/shuffle/?remaining=true")
    void shuffle(@PathVariable String deckId);

    @GetMapping("/{deckId}/draw/?count=5")
    DrawCardsResponse drawCards(@PathVariable String deckId);

    @GetMapping("/{deckId}/pile/{pileKey}/add/")
    DrawCardsResponse createPlayerHand(@PathVariable String deckId, @PathVariable int pileKey,
            @RequestParam String cards);

    @GetMapping("/{deckId}/pile/{pileKey}/list")
    DeckHandResponse getPlayerHand(@PathVariable String deckId, @PathVariable int pileKey);
}
