package org.gift.randomizer.app;

import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.model.GiftIdea;
import org.gift.randomizer.app.model.Participant;
import org.gift.randomizer.app.utils.JsonUtils;
import org.gift.randomizer.app.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

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
    public InMemoryDB getPersonsMockDB() {
        final LinkedList<Participant> participants = new LinkedList<>();
        participants.add(new Participant(1L, "Janusz", List.of(new GiftIdea("passerati"))));
        participants.add(new Participant(2L, "Grażynka", List.of(new GiftIdea("djament"))));
        return new InMemoryDB(participants);
    }
}
