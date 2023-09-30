package com.card.challenge.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rounds")
@Entity
public class RoundEntity extends DefaultBaseEntity {

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "finalized_at")
    private OffsetDateTime finalizedAt;

    @Column(name = "deck_id")
    private String deckId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "round")
    private List<PlayerEntity> players = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "round")
    private List<RoundWinnerEntity> winners = new ArrayList<>();
}
