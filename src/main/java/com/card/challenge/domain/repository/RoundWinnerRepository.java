package com.card.challenge.domain.repository;

import com.card.challenge.domain.entity.RoundWinnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundWinnerRepository extends JpaRepository<RoundWinnerEntity, Integer> {
}
