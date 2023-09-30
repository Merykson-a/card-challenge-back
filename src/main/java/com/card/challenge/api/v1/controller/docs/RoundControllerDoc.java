package com.card.challenge.api.v1.controller.docs;

import com.card.challenge.api.v1.io.RoundStartRequest;
import com.card.challenge.api.v1.io.RoundStartResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

@Api(tags = "Round controller")
@ApiResponses({@ApiResponse(code = 500, message = "There was an internal error", response = Problem.class)})
@SuppressWarnings("unused")
public interface RoundControllerDoc {

    @ApiOperation("Initialize round")
    @ApiResponses({@ApiResponse(code = 200, message = "Round started"), @ApiResponse(code = 400, message = "Invalid data", response = Problem.class), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found", response = Problem.class)})
    ResponseEntity<RoundStartResponse> start(@ApiParam(name = "body", value = "Representation of a new round with four players", required = true) RoundStartRequest request);
}
