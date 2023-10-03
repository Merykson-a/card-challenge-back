package com.card.challenge.api.v1.io.deck;

import com.card.challenge.domain.utils.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;

@Component
public class ExternalDeckAdapter {

    public List<CardResponse> getCardsByHandAndHandKey(ExternalDeckHandResponse deckHands, int handKey) {
        String json = Json.pretty(deckHands.getPiles());
        Iterator<JsonNode> cards = getCardsJsonNodeByGeneralJsonAndHandKey(json, handKey);

        if (cards != null) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(cards, 0), false)
                                .map(this::getCardResponseByCardNode)
                                .collect(Collectors.toList());
        }
        return emptyList();
    }

    private Iterator<JsonNode> getCardsJsonNodeByGeneralJsonAndHandKey(String json, int handKey) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json).get(String.valueOf(handKey)).get("cards").elements();
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(Message.toLocale("ExternalDeck.FailedToGetCards"));
        }
    }

    private CardResponse getCardResponseByCardNode(JsonNode node) {
        return CardResponse.builder()
                           .code(node.get("code").asText())
                           .image(node.get("image").asText())
                           .value(node.get("value").asText())
                           .build();
    }
}
