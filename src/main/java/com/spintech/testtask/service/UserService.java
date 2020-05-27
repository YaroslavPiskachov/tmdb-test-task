package com.spintech.testtask.service;

import com.spintech.testtask.dto.RegisterDto;
import com.spintech.testtask.dto.UserDto;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.exception.ActorNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerUser(RegisterDto registerDto);

    User findUser(String email);

    User saveUser(User user);

    UserDto addFavoriteActor(String actorId) throws ActorNotFoundException;

    UserDto deleteFavoriteActor(String actorId) throws ActorNotFoundException;
}

