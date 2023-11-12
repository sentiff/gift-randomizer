package org.gift.randomizer.app.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.gift.randomizer.app.model.GiftIdea;
import org.gift.randomizer.app.model.Observation;
import org.gift.randomizer.app.model.Participant;
import org.gift.randomizer.app.model.Person;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class InMemoryDB {

    private final LinkedList<Participant> participants;
    @Getter
    private final LinkedList<Observation> observations;
    private final Random randomGenerator;

    public Participant getParticipantById(Long id) throws NoCandidateException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getId().equals(id))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new NoCandidateException("no candidate with id: %s".formatted(id));
        }
    }

    public Participant getParticipantByName(String name) throws NoCandidateException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getName().equals(name))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new NoCandidateException("no candidate with name: %s".formatted(name));
        }
    }

    public Response addParticipant(String name, List<String> rawGiftIdeas) {
        val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
        val nextAvailableId = participants.getLast().getId() + 1;
        participants.add(new Participant(nextAvailableId, name, mappedGiftIdeas));
        return new Response("added with id: %s".formatted(nextAvailableId), "200");
    }

    public Response updateParticipantById(Long id, String name) {
        try {
            val foundParticipant = getParticipantById(id);
            removeParticipantById(id);
            val updatedParticipant = new Participant(foundParticipant.getId(), name, foundParticipant.getGiftIdeas());
            participants.add(updatedParticipant);
            return new Response("updated participant with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response updateParticipantById(Long id, List<String> rawGiftIdeas) {
        try {
            val foundParticipant = getParticipantById(id);
            removeParticipantById(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedParticipant = new Participant(foundParticipant.getId(), foundParticipant.getName(), mappedGiftIdeas);
            participants.add(updatedParticipant);
            return new Response("updated candidate with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response updateParticipantById(Long id, String name, List<String> rawGiftIdeas) {
        try {
            val foundParticipant = getParticipantById(id);
            removeParticipantById(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedParticipant = new Participant(foundParticipant.getId(), name, mappedGiftIdeas);
            participants.add(updatedParticipant);
            return new Response("updated participant with id: %s".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response removeParticipantById(Long id) {
        try {
            val foundParticipant = getParticipantById(id);
            participants.remove(foundParticipant);
            return new Response("participant with id: %s removed".formatted(id), "200");
        } catch (NoCandidateException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response createObservations(Boolean isRecreate) throws ObservationsAlreadyExisitngException {
        if (isRecreate || observations.isEmpty()) {
            observations.clear();
            generateObservations();
            return new Response(":)", "200");
        } else {
            throw new ObservationsAlreadyExisitngException("observations already generated");
        }
    }


    private void generateObservations() {
        try {
            var generatedNumbers = new ArrayList<>();
            participants.forEach(
                    participant -> {
                        do {
                            val generatedNumber = randomGenerator.nextInt(participants.size());
                            if (!generatedNumbers.contains(generatedNumber) && !participants.get(generatedNumber).equals(participant)) {
                                generatedNumbers.add(generatedNumber);
                                observations.add(
                                        new Observation(
                                                new Person(participant.getId(), participant.getName()),
                                                participants.get(generatedNumber)
                                        )
                                );
                                break;
                            }
                        } while (true);
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
