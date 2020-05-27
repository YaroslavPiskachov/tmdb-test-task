package com.spintech.testtask.service;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.exception.ActorNotFoundException;

public interface TmdbApiService {
    String popularTVShows();

    Actor getTmdbActor(String actorId) throws ActorNotFoundException;
}
