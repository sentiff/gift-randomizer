package org.sentiff.gift.randomizer.commons.storage;


import lombok.val;
import org.sentiff.gift.randomizer.commons.model.Observation;
import org.sentiff.gift.randomizer.commons.model.Response;
import org.sentiff.gift.randomizer.commons.model.exceptions.ObservationsException;

import java.util.List;

public interface ObservationRepository {

    default String describe() {
        val currentName = ObservationRepository.class.getSimpleName();
        return String.format("interface: %s", currentName);
    }

    List<Observation> getObservations() throws ObservationsException;

    Response removeObservations() throws ObservationsException;

    Response createObservations(Boolean isRecreate) throws ObservationsException;

    Observation getObservation(Long id) throws ObservationsException;

    Observation getObservation(String name) throws ObservationsException;
}
