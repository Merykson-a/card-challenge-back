package com.card.challenge.api.exception_handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @Schema(example = "400")
    private final Integer status;

    @Schema(example = "2023-10-03T15:34:20.6Z")
    private final OffsetDateTime timestamp;

    @Schema(example = "One or more fields are invalid. Fill in correctly and try again.")
    private final String detail;
}
