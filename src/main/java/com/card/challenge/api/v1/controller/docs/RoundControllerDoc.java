package com.card.challenge.api.v1.controller.docs;

import com.card.challenge.api.v1.io.round.RoundStartRequest;
import com.card.challenge.api.v1.io.round.RoundStartResponse;
import com.card.challenge.api.v1.io.round.player.PlayerDrawnCardsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unused")
@Tag(name = "Round controller", description = "Round management")
public interface RoundControllerDoc {

    @Operation(summary = "Initialize round")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Round started"),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                         content = @Content(schema = @Schema(implementation = Problem.class),
                                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                         content = @Content(schema = @Schema(implementation = Problem.class),
                                            mediaType = "application/json"))})
    ResponseEntity<RoundStartResponse> start(
            @Parameter(name = "body", description = "Representation of a new round with four players", required = true)
            RoundStartRequest request);

    @Operation(summary = "Draw cards of round deck")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Drawed cards"),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                         content = @Content(schema = @Schema(implementation = Problem.class),
                                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found",
                         content = @Content(schema = @Schema(implementation = Problem.class),
                                            mediaType = "application/json"))})
    ResponseEntity<PlayerDrawnCardsResponse> drawCards(
            @Parameter(name = "playerId", example = "1", required = true) int playerId);
}

