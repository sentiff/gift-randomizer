package org.gift.randomizer.app;

import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.model.GiftIdea;
import org.gift.randomizer.app.model.Observation;
import org.gift.randomizer.app.model.Participant;
import org.gift.randomizer.app.utils.JsonUtils;
import org.gift.randomizer.app.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Configuration
public class BeanProvider {

    @Bean
    public JsonUtils getJsonUtils() {
        return new JsonUtils();
    }

    @Bean
    public ResponseUtils getResponseUtils() {
        return new ResponseUtils();
    }

    @Bean
    public InMemoryDB getInMemoryDB() {
        final LinkedList<Participant> participants = new LinkedList<>();
        participants.add(new Participant(1L, "Janusz", List.of(new GiftIdea("passerati"))));
        participants.add(new Participant(2L, "Grażynka", List.of(new GiftIdea("djament"))));
        participants.add(new Participant(3L, "Pjoter", List.of(new GiftIdea("ajfon"))));
        final LinkedList<Observation> observations = new LinkedList<>();
        final Random randomGenerator = new Random();
        return new InMemoryDB(participants, observations, randomGenerator);
    }
}
