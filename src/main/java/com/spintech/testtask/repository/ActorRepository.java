package com.spintech.testtask.repository;

import com.spintech.testtask.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByCreditId(String creditId);
}
