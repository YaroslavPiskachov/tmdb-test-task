package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.repository.ActorRepository;
import com.spintech.testtask.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorInterfaceImpl implements ActorService {

    private ActorRepository actorRepository;

    @Autowired
    public ActorInterfaceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor getActorByCreditId(String creditId) {
        return actorRepository.findByCreditId(creditId);
    }
}
