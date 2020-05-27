package com.spintech.testtask.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spintech.testtask.util.ActorDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ActorDeserializer.class)
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String creditId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Gender gender;

    @ManyToMany(mappedBy = "favoriteActors")
    private Set<User> fans;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
