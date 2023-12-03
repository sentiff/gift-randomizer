package org.sentiff.gift.randomizer.db;

import io.swagger.v3.oas.models.links.Link;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.sentiff.gift.randomizer.db.model.*;
import org.sentiff.gift.randomizer.db.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.db.model.exceptions.ParticipantException;

import java.util.*;

@AllArgsConstructor
public class InMemoryDB {

    @Getter
    private final LinkedList<Participant> participants;
    @Getter
    private final LinkedList<Observation> observations;
    private final Random randomGenerator;

    public Participant getParticipantById(Long id) throws ParticipantException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getId().equals(id))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new ParticipantException("no participant with id: %s".formatted(id));
        }
    }

    public Participant getParticipantByName(String name) throws ParticipantException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getName().equals(name))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new ParticipantException("no participant with name: %s".formatted(name));
        }
    }

    public Response addParticipant(String name, List<String> rawGiftIdeas) {
        val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
        long nextAvailableId;
        try {
            nextAvailableId = participants.getLast().getId() + 1;
        } catch (NoSuchElementException e) {
            nextAvailableId = 1;
        }
        participants.add(new Participant(nextAvailableId, name, mappedGiftIdeas));
        return new Response("added participant with id: %s".formatted(nextAvailableId), "200");
    }

    public Response updateParticipantById(Long id, String name) {
        try {
            val foundParticipant = getParticipantById(id);
            removeParticipantById(id);
            val updatedParticipant = new Participant(foundParticipant.getId(), name, foundParticipant.getGiftIdeas());
            participants.add(updatedParticipant);
            return new Response("updated participant with id: %s".formatted(id), "200");
        } catch (ParticipantException e) {
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
        } catch (ParticipantException e) {
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
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response removeParticipantById(Long id) {
        try {
            val foundParticipant = getParticipantById(id);
            participants.remove(foundParticipant);
            return new Response("participant with id: %s removed".formatted(id), "200");
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public Response removeObservations() throws ObservationsException {
        if (!observations.isEmpty()) {
            observations.clear();
            return new Response("observations removed", "200");
        } else {
            throw new ObservationsException("no observations to remove");
        }
    }

    public Response createObservations(Boolean isRecreate) throws ObservationsException {
        if (isRecreate || observations.isEmpty()) {
            observations.clear();
            generateObservations();
            val re = isRecreate ? "re-" : "";
            return new Response("%s observations %screated".formatted(observations.size(), re), "200");
        } else {
            throw new ObservationsException("observations already generated");
        }
    }

    public Observation getObservationById(Long id) throws ObservationsException {
        val foundObservation = observations.stream()
                .filter(observation -> observation.who().getId().equals(id))
                .toList();
        if (!foundObservation.isEmpty()) {
            return foundObservation.get(0);
        } else {
            throw new ObservationsException("no observation with id: %s".formatted(id));
        }
    }

    public Observation getObservationByName(String name) throws ObservationsException {
        val foundObservation = observations.stream()
                .filter(observation -> observation.who().getName().equals(name))
                .toList();
        if (!foundObservation.isEmpty()) {
            return foundObservation.get(0);
        } else {
            throw new ObservationsException("no observation with name: %s".formatted(name));
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
