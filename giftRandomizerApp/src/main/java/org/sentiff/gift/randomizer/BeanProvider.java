package org.sentiff.gift.randomizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.sentiff.gift.randomizer.commons.db.InMemoryDB;
import org.sentiff.gift.randomizer.commons.db.Storage;
import org.sentiff.gift.randomizer.commons.db.model.GiftIdea;
import org.sentiff.gift.randomizer.commons.db.model.Observation;
import org.sentiff.gift.randomizer.commons.db.model.Participant;
import org.sentiff.gift.randomizer.commons.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Configuration
public class BeanProvider {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("gift randomizer API")
                        .version("0.0.3"));
    }

    @Bean
    public JsonUtils getJsonUtils() {
        return new JsonUtils();
    }

    @Bean
    public ResponseUtils getResponseUtils() {
        return new ResponseUtils();
    }

    @Bean
    public Storage getInMemoryDB() {
        final LinkedList<Participant> participants = new LinkedList<>();
        participants.add(new Participant(1L, "Janusz", List.of(new GiftIdea("passerati"))));
        participants.add(new Participant(2L, "Grażynka", List.of(new GiftIdea("djament"))));
        participants.add(new Participant(3L, "Pjoter", List.of(new GiftIdea("ajfon"))));
        final LinkedList<Observation> observations = new LinkedList<>();
        final Random randomGenerator = new Random();
        return new InMemoryDB(participants, observations, randomGenerator);
    }
}
