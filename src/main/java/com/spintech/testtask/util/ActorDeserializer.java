package com.spintech.testtask.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.Gender;

import java.io.IOException;

public class ActorDeserializer extends StdDeserializer<Actor> {

    public ActorDeserializer(Class<?> vc) {
        super(vc);
    }

    public ActorDeserializer() {
        this(null);
    }

    @Override
    public Actor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode personNode = node.get("person");

        Actor actor = new Actor();
        actor.setGender(Gender.getByValue(personNode.get("gender").asInt()));
        actor.setName(personNode.get("name").asText());
        actor.setCreditId(node.get("id").asText());

        return actor;
    }
}
