package org.sentiff.gift.randomizer.commons.db;

import lombok.AllArgsConstructor;
import lombok.val;
import org.javatuples.Pair;
import org.sentiff.gift.randomizer.commons.db.model.*;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ParticipantException;

import java.util.*;

@AllArgsConstructor
public class InMemoryDB implements Storage {

    private final LinkedList<Participant> participants;
    private final LinkedList<Observation> observations;
    private final Random randomGenerator;

    @Override
    public List<Participant> getParticipants() {
        return participants;
    }

    @Override
    public Participant getParticipant(Long id) throws ParticipantException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getId().equals(id))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new ParticipantException("no participant with id: %s".formatted(id));
        }
    }

    @Override
    public Participant getParticipant(String name) throws ParticipantException {
        val foundParticipant = participants.stream()
                .filter(participant -> participant.getName().equals(name))
                .toList();

        if (!foundParticipant.isEmpty()) {
            return foundParticipant.get(0);
        } else {
            throw new ParticipantException("no participant with name: %s".formatted(name));
        }
    }

    @Override
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

    @Override
    public Response updateParticipant(Long id, String name) {
        try {
            val foundParticipant = getParticipant(id);
            removeParticipant(id);
            val updatedParticipant = new Participant(foundParticipant.getId(), name, foundParticipant.getGiftIdeas());
            participants.add(updatedParticipant);
            return new Response("updated participant with id: %s".formatted(id), "200");
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    @Override
    public Response updateParticipant(Long id, List<String> rawGiftIdeas) {
        try {
            val foundParticipant = getParticipant(id);
            removeParticipant(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedParticipant = new Participant(foundParticipant.getId(), foundParticipant.getName(), mappedGiftIdeas);
            participants.add(updatedParticipant);
            return new Response("updated candidate with id: %s".formatted(id), "200");
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    @Override
    public Response updateParticipant(Long id, String name, List<String> rawGiftIdeas) {
        try {
            val foundParticipant = getParticipant(id);
            removeParticipant(id);
            val mappedGiftIdeas = rawGiftIdeas.stream().map(GiftIdea::new).toList();
            val updatedParticipant = new Participant(foundParticipant.getId(), name, mappedGiftIdeas);
            participants.add(updatedParticipant);
            return new Response("updated participant with id: %s".formatted(id), "200");
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    @Override
    public Response removeParticipant(Long id) throws ParticipantException {
        try {
            val foundParticipant = getParticipant(id);
            participants.remove(foundParticipant);
            return new Response("participant with id: %s removed".formatted(id), "200");
        } catch (ParticipantException e) {
            return new Response("cannot remove, participant with id: %s not found".formatted(id), "204");
        }
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public Response removeObservations() throws ObservationsException {
        if (!observations.isEmpty()) {
            observations.clear();
            return new Response("observations removed", "200");
        } else {
            throw new ObservationsException("no observations to remove");
        }
    }

    @Override
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

    @Override
    public Observation getObservation(Long id) throws ObservationsException {
        val foundObservation = observations.stream()
                .filter(observation -> observation.who().getId().equals(id))
                .toList();
        if (!foundObservation.isEmpty()) {
            return foundObservation.get(0);
        } else {
            throw new ObservationsException("no observation with id: %s".formatted(id));
        }
    }

    @Override
    public Observation getObservation(String name) throws ObservationsException {
        val foundObservation = observations.stream()
                .filter(observation -> observation.who().getName().equals(name))
                .toList();
        if (!foundObservation.isEmpty()) {
            return foundObservation.get(0);
        } else {
            throw new ObservationsException("no observation with name: %s".formatted(name));
        }
    }

    private void generateObservations() throws ObservationsException {
        try {
            ArrayList<Pair<Long, Long>> pairs;
            do {
                pairs = generatePairs();
            } while (!arePairsUnique(pairs));
            convertPairsToObservations(pairs);
        } catch (Exception e) {
            throw new ObservationsException(e.getMessage());
        }
    }

    private Boolean arePairsUnique(ArrayList<Pair<Long, Long>> pairs) {
        return pairs.stream()
                .filter(pair -> pair.getValue0().equals(pair.getValue1()))
                .toList()
                .isEmpty();
    }

    private ArrayList<Pair<Long, Long>> generatePairs() {
        val generatedNumbers = new ArrayList<Long>();
        val pairs = new ArrayList<Pair<Long, Long>>();
        participants.forEach(
                participant -> {
                    do {
                        val generatedNumber = randomGenerator.nextLong(1, participants.getLast().getId() + 1);
                        if (!generatedNumbers.contains(generatedNumber)) {
                            generatedNumbers.add(generatedNumber);
                            pairs.add(new Pair<>(participant.getId(), generatedNumber));
                            break;
                        }
                    } while (true);
                });
        return pairs;
    }

    private void convertPairsToObservations(ArrayList<Pair<Long, Long>> pairs) {
        pairs.forEach(pair ->
                {
                    try {
                        val who = getParticipant(pair.getValue0());
                        val toWhom = getParticipant(pair.getValue1());
                        observations.add(
                                new Observation(
                                        new Person(who.getId(), who.getName()),
                                        getParticipant(toWhom.getId())
                                ));
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        );
    }
}
