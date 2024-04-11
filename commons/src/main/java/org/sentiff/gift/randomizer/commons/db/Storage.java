package org.sentiff.gift.randomizer.commons.db;

import org.sentiff.gift.randomizer.commons.db.model.Observation;
import org.sentiff.gift.randomizer.commons.db.model.Participant;
import org.sentiff.gift.randomizer.commons.db.model.Response;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ParticipantException;

import java.util.List;

public interface Storage {
    Participant getParticipant(Long id) throws ParticipantException;

    Participant getParticipant(String name) throws ParticipantException;

    Response addParticipant(String name, List<String> rawGiftIdeas) throws ParticipantException;

    Response updateParticipant(Long id, String name) throws ParticipantException;

    Response updateParticipant(Long id, List<String> rawGiftIdeas) throws ParticipantException;

    Response updateParticipant(Long id, String name, List<String> rawGiftIdeas) throws ParticipantException;

    Response removeParticipant(Long id) throws ParticipantException;

    Response removeObservations() throws ObservationsException;

    Response createObservations(Boolean isRecreate) throws ObservationsException;

    Observation getObservation(Long id) throws ObservationsException;

    Observation getObservation(String name) throws ObservationsException;
}