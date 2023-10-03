package com.card.challenge.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players")
@Entity
public class PlayerEntity extends DefaultBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "play_date")
    private LocalDateTime playDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private RoundEntity round;
}
