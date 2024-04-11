package org.sentiff.gift.randomizer.graphql.controllers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.sentiff.gift.randomizer.commons.db.Storage;
import org.sentiff.gift.randomizer.commons.db.model.GiftIdeaInput;
import org.sentiff.gift.randomizer.commons.db.model.Participant;
import org.sentiff.gift.randomizer.commons.db.model.exceptions.ParticipantException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

@DgsComponent
public class ParticipantController {

    @Autowired
    private Storage inMemoryDB;

    @DgsQuery
    public List<Participant> participantByName(@InputArgument String name) throws ParticipantException {
        if (name == null) {
            return inMemoryDB.getParticipants();
        }
        return inMemoryDB.getParticipants().stream().filter(element -> element.getName().contains(name)).toList();
    }

    @DgsQuery
    public List<Participant> participantById(@InputArgument Integer id) throws ParticipantException {
        if (id == null) {
            return inMemoryDB.getParticipants();
        }
        return inMemoryDB.getParticipants().stream().filter(element -> element.getId().equals(Long.valueOf(id))).toList();
    }

    @DgsMutation
    public String addParticipant(@InputArgument String name, @InputArgument List<LinkedHashMap<String, String>> giftIdeas) throws ParticipantException {
        inMemoryDB.addParticipant(name, giftIdeas.stream().map(element -> element.get("rawText")).toList());
        return "success";
    }

    @DgsMutation
    public String updateParticipantName(@InputArgument Integer id, @InputArgument String name) throws ParticipantException {
        inMemoryDB.updateParticipant(Long.valueOf(id), name);
        return "success";
    }

    @DgsMutation
    public void updateParticipantGiftIdeas(@InputArgument Integer id, @InputArgument List<GiftIdeaInput> giftIdeas) throws ParticipantException {
        inMemoryDB.updateParticipant(Long.valueOf(id), giftIdeas.stream().map(GiftIdeaInput::getRawText).toList());
    }

    @DgsMutation
    public void removeParticipant(@InputArgument Integer id) throws ParticipantException {
        inMemoryDB.removeParticipant(Long.valueOf(id));
    }
}
