package com.card.challenge.domain.service.player.impl;

import com.card.challenge.api.exception_handler.IllegalValueException;
import com.card.challenge.domain.entity.PlayerEntity;
import com.card.challenge.domain.repository.PlayerRepository;
import com.card.challenge.domain.service.external_deck.ExternalDeckService;
import com.card.challenge.domain.service.player.PlayerService;
import com.card.challenge.domain.utils.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ExternalDeckService externalDeckService;

    @Override
    public PlayerEntity getById(int playerId) {
        Optional<PlayerEntity> optional = playerRepository.findById(playerId);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new EntityNotFoundException(Message.toLocale("Player.Error.NotFound"));
    }

    @Override
    public PlayerEntity drawCardsById(int playerId) {
        PlayerEntity player = getById(playerId);
        String roundDeckId = player.getRound().getDeckId();

        if (player.getPlayDate() != null) {
            throw new IllegalValueException(Message.toLocale("Player.Error.PlayAlreadyMade"));
        }

        externalDeckService.drawCards(roundDeckId, player.getId());
        player.setPlayDate(now());
        return playerRepository.save(player);
    }
}
