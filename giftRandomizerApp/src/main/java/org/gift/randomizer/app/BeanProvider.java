package org.gift.randomizer.app;

import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.model.GiftIdea;
import org.gift.randomizer.app.model.Person;
import org.gift.randomizer.app.utils.JsonUtils;
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
    public InMemoryDB getPersonsMockDB() {
        final LinkedList<Person> persons = new LinkedList<>();
        persons.add(new Person(1L, "Janusz", List.of(new GiftIdea("passerati"))));
        persons.add(new Person(2L, "Grażynka", List.of(new GiftIdea("djament"))));
        return new InMemoryDB(persons);
    }
}
