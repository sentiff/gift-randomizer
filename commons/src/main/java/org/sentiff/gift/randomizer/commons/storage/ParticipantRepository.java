package org.sentiff.gift.randomizer.commons.storage;

import lombok.val;
import org.sentiff.gift.randomizer.commons.model.Participant;
import org.sentiff.gift.randomizer.commons.model.Response;
import org.sentiff.gift.randomizer.commons.model.exceptions.ParticipantException;

import java.util.List;

public interface ParticipantRepository {

    default String describe() {
        val currentName = ParticipantRepository.class.getSimpleName();
        return String.format("interface: %s", currentName);
    }

    List<Participant> getParticipants() throws ParticipantException;

    Participant getParticipant(Long id) throws ParticipantException;

    Participant getParticipant(String name) throws ParticipantException;

    Response addParticipant(String name, List<String> rawGiftIdeas) throws ParticipantException;

    Response updateParticipant(Long id, String name) throws ParticipantException;

    Response updateParticipant(Long id, List<String> rawGiftIdeas) throws ParticipantException;

    Response updateParticipant(Long id, String name, List<String> rawGiftIdeas) throws ParticipantException;

    Response removeParticipant(Long id) throws ParticipantException;
}
