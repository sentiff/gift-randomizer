package org.gift.randomizer.app.db;

import lombok.AllArgsConstructor;
import lombok.val;
import org.gift.randomizer.app.model.GiftIdea;
import org.gift.randomizer.app.model.Person;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class InMemoryDB {

    private LinkedList<Person> persons;

    public Person getPersonById(Long id) throws NoCandidateException {
        val foundPerson = persons.stream()
                .filter(person -> person.getId().equals(id))
                .toList();

        if (!foundPerson.isEmpty()) {
            return foundPerson.get(0);
        } else {
            throw new NoCandidateException("no candidate with id: %s".formatted(id));
        }
    }

    public Person getPersonByName(String name) throws NoCandidateException {
        val foundPerson = persons.stream()
                .filter(person -> person.getName().equals(name))
                .toList();

        if (!foundPerson.isEmpty()) {
            return foundPerson.get(0);
        } else {
            throw new NoCandidateException("no candidate with name: %s".formatted(name));
        }
    }

    public Response addPerson(String name, List<String> rawGiftIdeas) {
        val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
        val nextAvailableId = persons.getLast().getId() + 1;
        persons.add(new Person(nextAvailableId, name, mappedGiftIdeas));
        return new Response("added with id: %s".formatted(nextAvailableId), "200");
    }

    public Response updatePersonById(Long id) {
        return new Response("not implemented", "501");
    }

    public Response removePersonById(Long id) {
        return new Response("not implemented", "501");
    }
}
