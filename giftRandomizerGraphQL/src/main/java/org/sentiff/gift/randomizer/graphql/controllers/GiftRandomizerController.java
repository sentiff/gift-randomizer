package org.sentiff.gift.randomizer.graphql.controllers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.val;
import org.sentiff.gift.randomizer.commons.db.InMemoryDB;
import org.sentiff.gift.randomizer.commons.db.model.Observation;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ObservationsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class GiftRandomizerController {
    @Autowired
    private InMemoryDB inMemoryDB;

    @DgsQuery
    public List<Observation> observations() {
        return inMemoryDB.getObservations();
    }

    @DgsQuery
    public Observation observationById(@InputArgument Integer id) throws ObservationsException {
        return inMemoryDB.getObservationById(Long.valueOf(id));
    }

    @DgsQuery
    public Observation observationByName(@InputArgument String name) throws ObservationsException {
        return inMemoryDB.getObservationByName(name);
    }

    @DgsMutation
    public String createObservations(@InputArgument Boolean areObservationsFair) {
        try {
            val response = inMemoryDB.createObservations(false);
            return response.code();
        } catch (ObservationsException e) {
            return e.getMessage();
        }
    }

    @DgsMutation
    public String recreateObservations(@InputArgument Boolean areObservationsFair) {
        try {
            val response = inMemoryDB.createObservations(true);
            return response.code();
        } catch (ObservationsException e) {
            return e.getMessage();
        }
    }

    @DgsMutation
    public String removeObservations() {
        try {
            val response = inMemoryDB.removeObservations();
            return response.code();
        } catch (ObservationsException e) {
            return e.getMessage();
        }
    }
}
