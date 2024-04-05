import lombok.val;
import org.junit.jupiter.api.Test;
import org.sentiff.gift.randomizer.commons.db.InMemoryDB;
import org.sentiff.gift.randomizer.commons.db.model.GiftIdea;
import org.sentiff.gift.randomizer.commons.db.model.Observation;
import org.sentiff.gift.randomizer.commons.db.model.Participant;
import org.sentiff.gift.randomizer.commons.db.model.Response;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ParticipantException;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryDBTest {


    private InMemoryDB createInMemoryDB(Boolean isEmpty) {
        val participants = new LinkedList<Participant>();
        val observations = new LinkedList<Observation>();
        val randomGenerator = new Random();
        if (!isEmpty) {
            participants.add(new Participant(1L, "Janusz", List.of(new GiftIdea("passerati"))));
            participants.add(new Participant(2L, "Grażynka", List.of(new GiftIdea("djament"))));
            participants.add(new Participant(3L, "Pjoter", List.of(new GiftIdea("ajfon"))));
        }
        return new InMemoryDB(participants, observations, randomGenerator);

    }


    @Test
    void getParticipantTest() throws ParticipantException {
        val tempDB = createInMemoryDB(false);

        val expectedParticipant = new Participant(1L, "Janusz", List.of(new GiftIdea("passerati")));

        var actualParticipant = tempDB.getParticipant("Janusz");
        assertEquals(expectedParticipant.getName(), actualParticipant.getName());

        actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());

        actualParticipant = tempDB.getParticipants().get(0);
        assertEquals(expectedParticipant.getGiftIdeas().get(0).rawText(), actualParticipant.getGiftIdeas().get(0).rawText());

        var expectedException = new ParticipantException("no participant with name: Andrzej");
        var actualException = assertThrows(ParticipantException.class, () -> tempDB.getParticipant("Andrzej"));
        assertEquals(expectedException.getMessage(), actualException.getMessage());

        expectedException = new ParticipantException("no participant with id: 100");
        actualException = assertThrows(ParticipantException.class, () -> tempDB.getParticipant(100L));
        assertEquals(expectedException.getMessage(), actualException.getMessage());

    }

    @Test
    void addParticipantTest() {
        var tempDB = createInMemoryDB(false);
        var actualResponse = tempDB.addParticipant("Mati", List.of("laptok"));
        var expectedResponse = new Response("added participant with id: 4", "200");
        assertEquals(expectedResponse.body(), actualResponse.body());
        assertEquals(expectedResponse.code(), actualResponse.code());

        tempDB = createInMemoryDB(true);
        actualResponse = tempDB.addParticipant("Mati", List.of("laptok"));
        expectedResponse = new Response("added participant with id: 1", "200");
        assertEquals(expectedResponse.body(), actualResponse.body());
    }

    @Test
    void updateParticipantTest() throws ParticipantException {
        var tempDB = createInMemoryDB(false);

        val expectedResponse = new Response("updated participant with id: 1", "204");
        var actualResponse = tempDB.updateParticipant(1L, "Andrzej");
        assertEquals(expectedResponse.body(), actualResponse.body());


        tempDB = createInMemoryDB(false);

        tempDB.updateParticipant(1L, "Andrzej", List.of("laptok"));

        var expectedParticipant = new Participant(1L, "Andrzej", List.of(new GiftIdea("laptok")));
        var actualParticipant = tempDB.getParticipant(1L);
        assertEquals(expectedParticipant.getName(), actualParticipant.getName());
        assertEquals(expectedParticipant.getId(), actualParticipant.getId());
        assertEquals(expectedParticipant.getGiftIdeas(), actualParticipant.getGiftIdeas());


    }

    @Test
    void createObservations() throws ObservationsException {
        var tempDB = createInMemoryDB(false);

        var expectedResponse = new Response("3 observations created", "200");
        var actualResponse = tempDB.createObservations(false);
        assertEquals(expectedResponse.body(), actualResponse.body());

        expectedResponse = new Response("3 observations re-created", "200");
        actualResponse = tempDB.createObservations(true);
        assertEquals(expectedResponse.body(), actualResponse.body());

        val expectedException = new ObservationsException("observations already generated");
        val actualException = assertThrows(ObservationsException.class, () -> tempDB.createObservations(false));
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}
