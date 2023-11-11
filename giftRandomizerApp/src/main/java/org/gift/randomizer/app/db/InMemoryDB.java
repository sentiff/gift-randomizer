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

    public Response updatePersonById(Long id, String name) {
        try {
            val foundPerson = getPersonById(id);
            removePersonById(id);
            val updatedPerson = new Person(foundPerson.getId(), name, foundPerson.getGiftIdeas());
            persons.add(updatedPerson);
            return new Response("updated candidate with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, candidate with id: %s not found".formatted(id), "204");
        }
    }

    public Response updatePersonById(Long id, List<String> rawGiftIdeas) {
        try {
            val foundPerson = getPersonById(id);
            removePersonById(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedPerson = new Person(foundPerson.getId(), foundPerson.getName(), mappedGiftIdeas);
            persons.add(updatedPerson);
            return new Response("updated candidate with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, candidate with id: %s not found".formatted(id), "204");
        }
    }

    public Response updatePersonById(Long id, String name, List<String> rawGiftIdeas) {
        try {
            val foundPerson = getPersonById(id);
            removePersonById(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedPerson = new Person(foundPerson.getId(), name, mappedGiftIdeas);
            persons.add(updatedPerson);
            return new Response("updated candidate with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, candidate with id: %s not found".formatted(id), "204");
        }
    }

    public Response removePersonById(Long id) {
        try {
            val foundPerson = getPersonById(id);
            persons.remove(foundPerson);
            return new Response("candidate with id: %s removed".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, candidate with id: %s not found".formatted(id), "204");
        }
    }
}
