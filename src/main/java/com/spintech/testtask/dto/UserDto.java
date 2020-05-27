package com.spintech.testtask.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private Set<ActorDto> favoriteActors;
}
