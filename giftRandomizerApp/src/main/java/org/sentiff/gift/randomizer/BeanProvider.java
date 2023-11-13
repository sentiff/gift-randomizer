package org.sentiff.gift.randomizer;

import org.sentiff.gift.randomizer.db.InMemoryDB;
import org.sentiff.gift.randomizer.db.model.GiftIdea;
import org.sentiff.gift.randomizer.db.model.Observation;
import org.sentiff.gift.randomizer.db.model.Participant;
import org.sentiff.gift.randomizer.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
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
