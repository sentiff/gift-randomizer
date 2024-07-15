import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sentiff.gift.randomizer.commons.model.*;
import org.sentiff.gift.randomizer.commons.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.commons.model.exceptions.ParticipantException;
import org.sentiff.gift.randomizer.memorydb.MemoryDB;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class MemoryDBTest {

    private final Participant JANUSZ = new Participant(1L, "Janusz", List.of(new GiftIdea("passerati")));
    private final Participant GRAZYNKA = new Participant(2L, "Grażynka", List.of(new GiftIdea("djament")));
    private final Participant PJOTER = new Participant(3L, "Pjoter", List.of(new GiftIdea("ajfon")));

    private final LinkedList<Participant> PARTICIPANTS = generateParticipants();
    private final LinkedList<Observation> OBSERVATIONS = generateObservations();

    private LinkedList<Participant> generateParticipants() {
        val participants = new LinkedList<Participant>();
        participants.add(JANUSZ);
        participants.add(GRAZYNKA);
        participants.add(PJOTER);
        return participants;
    }

    private LinkedList<Observation> generateObservations() {
        val observations = new LinkedList<Observation>();
        observations.add(new Observation(
                new Person(2L, "Grażynka"),
                JANUSZ
        ));
        observations.add(new Observation(
                new Person(3L, "Pjoter"),
                GRAZYNKA
        ));
        observations.add(new Observation(
                new Person(1L, "Janusz"),
                PJOTER
        ));
        return observations;
    }

    private MemoryDB createInMemoryDB(Boolean areParticipants, Boolean areObservations) {
        var participants = new LinkedList<Participant>();
        var observations = new LinkedList<Observation>();
        val randomGenerator = new Random();

        if (areParticipants) {
            participants = PARTICIPANTS;
        }
        if (areObservations) {
            observations = OBSERVATIONS;
        }

        return new MemoryDB(participants, observations, randomGenerator);
    }

    @Test
    void getParticipantTest() throws ParticipantException {
        val tempDB = createInMemoryDB(true, false);

        val expectedParticipant = JANUSZ;

        var actualParticipant = tempDB.getParticipant("Janusz");
        assertEquals(expectedParticipant.getName(), actualParticipant.getName());

        actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());

        actualParticipant = tempDB.getParticipants().get(0);
        assertEquals(expectedParticipant.getGiftIdeas().get(0).rawText(), actualParticipant.getGiftIdeas().get(0).rawText());

        var expectedException = new ParticipantException("no participant with name: Andrzej");
        var actualException = Assertions.assertThrows(ParticipantException.class, () -> tempDB.getParticipant("Andrzej"));
        assertEquals(expectedException.getMessage(), actualException.getMessage());

        expectedException = new ParticipantException("no participant with id: 100");
        actualException = Assertions.assertThrows(ParticipantException.class, () -> tempDB.getParticipant(100L));
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void addParticipantTest() {
        var tempDB = createInMemoryDB(true, false);
        var actualResponse = tempDB.addParticipant("Mati", List.of("laptok"));
        var expectedResponse = new Response("added participant with id: 4", "200");
        assertEquals(expectedResponse.body(), actualResponse.body());
        assertEquals(expectedResponse.code(), actualResponse.code());

        tempDB = createInMemoryDB(false, true);
        actualResponse = tempDB.addParticipant("Mati", List.of("laptok"));
        expectedResponse = new Response("added participant with id: 1", "200");
        assertEquals(expectedResponse.body(), actualResponse.body());
    }

    @Test
    void updateParticipantTest() throws ParticipantException {
        var tempDB = createInMemoryDB(true, false);

        val expectedResponse = new Response("updated participant with id: 1", "204");
        var actualResponse = tempDB.updateParticipant(1L, "Andrzej");
        assertEquals(expectedResponse.body(), actualResponse.body());

        tempDB = createInMemoryDB(true, false);

        tempDB.updateParticipant(1L, "Andrzej", List.of("laptok"));

        var expectedParticipant = new Participant(1L, "Andrzej", List.of(new GiftIdea("laptok")));
        var actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getName(), actualParticipant.getName());
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());
        assertEquals(expectedParticipant.getGiftIdeas(), actualParticipant.getGiftIdeas());

        tempDB.updateParticipant(1L, "Mariusz");

        expectedParticipant = new Participant(1L, "Mariusz", List.of(new GiftIdea("passerati")));
        actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getName(), actualParticipant.getName());
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());

        tempDB.updateParticipant(1L, List.of("ponton"));

        expectedParticipant = new Participant(1L, "Janusz", List.of(new GiftIdea("ponton")));
        actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());
        assertEquals(expectedParticipant.getGiftIdeas(), actualParticipant.getGiftIdeas());
    }

    @Test
    void getObservationsTest() throws ObservationsException {
        var tempDB = createInMemoryDB(true, true);

        val actualObservations = tempDB.getObservations();
        Assertions.assertEquals(OBSERVATIONS, actualObservations);


        var expectedObservation = OBSERVATIONS.get(0);
        var actualObservation = tempDB.getObservation(2L);
        assertEquals(expectedObservation, actualObservation);

        expectedObservation = OBSERVATIONS.get(2);
        actualObservation = tempDB.getObservation("Janusz");
        assertEquals(expectedObservation, actualObservation);
    }

    @Test
    void removeObservationsTest() throws ObservationsException {
        var tempDB = createInMemoryDB(true, true);

        var expectedResponse = new Response("observations removed", "200");
        val actualResponse = tempDB.removeObservations();
        assertEquals(expectedResponse, actualResponse);

        tempDB = createInMemoryDB(true, false);

        var expectedException = new ObservationsException("no observations to remove");
        var actualException = Assertions.assertThrows(ObservationsException.class, tempDB::removeObservations);
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void createObservationsTest() throws ObservationsException {
        var tempDB = createInMemoryDB(true, false);

        var expectedResponse = new Response("3 observations created", "200");
        var actualResponse = tempDB.createObservations(false);
        assertEquals(expectedResponse.body(), actualResponse.body());

        expectedResponse = new Response("3 observations re-created", "200");
        actualResponse = tempDB.createObservations(true);
        assertEquals(expectedResponse.body(), actualResponse.body());

        val expectedException = new ObservationsException("observations already generated");
        val actualException = Assertions.assertThrows(ObservationsException.class, () -> tempDB.createObservations(false));
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}