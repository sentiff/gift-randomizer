package org.sentiff.gift.randomizer.sqlitedb;

import org.sentiff.gift.randomizer.commons.Storage;
import org.sentiff.gift.randomizer.commons.model.Observation;
import org.sentiff.gift.randomizer.commons.model.Participant;
import org.sentiff.gift.randomizer.commons.model.Response;
import org.sentiff.gift.randomizer.commons.model.exceptions.ObservationsException;
import org.sentiff.gift.randomizer.commons.model.exceptions.ParticipantException;

import java.util.List;

public class SqliteDB implements Storage {
    @Override
    public List<Participant> getParticipants() throws ParticipantException {
        return List.of();
    }

    @Override
    public Participant getParticipant(Long id) throws ParticipantException {
        return null;
    }

    @Override
    public Participant getParticipant(String name) throws ParticipantException {
        return null;
    }

    @Override
    public Response addParticipant(String name, List<String> rawGiftIdeas) throws ParticipantException {
        return null;
    }

    @Override
    public Response updateParticipant(Long id, String name) throws ParticipantException {
        return null;
    }

    @Override
    public Response updateParticipant(Long id, List<String> rawGiftIdeas) throws ParticipantException {
        return null;
    }

    @Override
    public Response updateParticipant(Long id, String name, List<String> rawGiftIdeas) throws ParticipantException {
        return null;
    }

    @Override
    public Response removeParticipant(Long id) throws ParticipantException {
        return null;
    }

    @Override
    public List<Observation> getObservations() throws ObservationsException {
        return List.of();
    }

    @Override
    public Response removeObservations() throws ObservationsException {
        return null;
    }

    @Override
    public Response createObservations(Boolean isRecreate) throws ObservationsException {
        return null;
    }

    @Override
    public Observation getObservation(Long id) throws ObservationsException {
        return null;
    }

    @Override
    public Observation getObservation(String name) throws ObservationsException {
        return null;
    }
}
