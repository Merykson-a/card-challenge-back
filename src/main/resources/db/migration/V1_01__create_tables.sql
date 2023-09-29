CREATE TABLE rounds
(
    id           INT         NOT NULL IDENTITY (1,1),
    created_at   DATETIME    NOT NULL,
    finalized_at DATETIME    NOT NULL,
    deck_id      VARCHAR(12) NOT NULL,

    CONSTRAINT round_id_pk PRIMARY KEY (id)
);

CREATE TABLE players
(
    id       INT          NOT NULL IDENTITY (1,1),
    name     VARCHAR(100) NOT NULL,
    round_id INT          NOT NULL,

    CONSTRAINT player_id_pk PRIMARY KEY (id),
    CONSTRAINT player_round_id_fk FOREIGN KEY (round_id) REFERENCES rounds (id)
)

CREATE TABLE round_winners
(
    id        INT NOT NULL IDENTITY (1,1),
    result    INT NOT NULL,
    round_id  INT NOT NULL,
    player_id INT NOT NULL,

    CONSTRAINT round_winner_id_pk PRIMARY KEY (id),
    CONSTRAINT round_winner_round_id_fk FOREIGN KEY (round_id) REFERENCES rounds (id),
    CONSTRAINT round_winner_player_id_fk FOREIGN KEY (player_id) REFERENCES players (id)
)
