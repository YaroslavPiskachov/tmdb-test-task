package com.spintech.testtask.dto;

import com.spintech.testtask.entity.Gender;
import lombok.Data;

@Data
public class ActorDto {
    private String id;
    private String creditId;
    private String name;
    private Gender gender;
}
