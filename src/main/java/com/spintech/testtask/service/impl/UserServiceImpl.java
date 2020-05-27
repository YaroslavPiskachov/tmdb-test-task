package com.spintech.testtask.service.impl;

import com.spintech.testtask.dto.ActorDto;
import com.spintech.testtask.dto.RegisterDto;
import com.spintech.testtask.dto.UserDto;
import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.exception.ActorNotFoundException;
import com.spintech.testtask.repository.UserRepository;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.TmdbApiService;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TmdbApiService tmdbApi;

    private final ActorService actorService;

    @Autowired
    public UserServiceImpl(ActorService actorService, TmdbApiService tmdbApi, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.actorService = actorService;
        this.tmdbApi = tmdbApi;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(RegisterDto registerDto) {
        String email = registerDto.getEmail();
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return null;
        }
        user = User.builder().email(email).password(passwordEncoder.encode(registerDto.getPassword())).build();
        return saveUser(user);
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto addFavoriteActor(String actorId) throws ActorNotFoundException {
        Actor actor = actorService.getActorByCreditId(actorId);

        if (actor == null) {
            actor = tmdbApi.getTmdbActor(actorId);
        }

        User user = getCurrentUser();
        user.getFavoriteActors().add(actor);

        return toUserDto(saveUser(user));
    }

    @Override
    public UserDto deleteFavoriteActor(String actorId) throws ActorNotFoundException {
        Actor actor = actorService.getActorByCreditId(actorId);

        if (actor == null) {
            throw new ActorNotFoundException(actorId);
        }

        User user = getCurrentUser();
        user.getFavoriteActors().remove(actor);

        return toUserDto(saveUser(user));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUser(s);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username: %s not found", s));
        }

        return user;
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUser(userDetails.getUsername());
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFavoriteActors(user
                .getFavoriteActors()
                .stream()
                .map(this::toActorDto)
                .collect(Collectors.toSet()));

        return userDto;
    }

    private ActorDto toActorDto(Actor actor) {
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getCreditId());
        actorDto.setGender(actor.getGender());
        actorDto.setName(actor.getName());

        return actorDto;
    }
}