package org.sentiff.gift.randomizer.graphql.controllers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.sentiff.gift.randomizer.commons.db.InMemoryDB;
import org.sentiff.gift.randomizer.commons.db.model.GiftIdea;
import org.sentiff.gift.randomizer.commons.db.model.GiftIdeaInput;
import org.sentiff.gift.randomizer.commons.db.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

@DgsComponent
public class ParticipantController {

    @Autowired
    private InMemoryDB inMemoryDB;

    @DgsQuery
    public List<Participant> participantByName(@InputArgument String name) {
        if (name == null) {
            return inMemoryDB.getParticipants();
        }
        return inMemoryDB.getParticipants().stream().filter(element -> element.getName().contains(name)).toList();
    }

    @DgsQuery
    public List<Participant> participantById(@InputArgument Integer id) {
        if (id == null) {
            return inMemoryDB.getParticipants();
        }
        return inMemoryDB.getParticipants().stream().filter(element -> element.getId().equals(Long.valueOf(id))).toList();
    }

    @DgsMutation
    public String addParticipant(@InputArgument String name, @InputArgument List<LinkedHashMap<String, String>> giftIdeas) {
        inMemoryDB.addParticipant(name, giftIdeas.stream().map(element -> element.get("rawText")).toList());
        return "success";
    }

    @DgsMutation
    public String updateParticipantName(@InputArgument Integer id, @InputArgument String name) {
        inMemoryDB.updateParticipantById(Long.valueOf(id), name);
        return "success";
    }

    @DgsMutation
    public void updateParticipantGiftIdeas(@InputArgument Integer id, @InputArgument List<GiftIdeaInput> giftIdeas) {
        inMemoryDB.updateParticipantById(Long.valueOf(id), giftIdeas.stream().map(GiftIdeaInput::getRawText).toList());
    }

    @DgsMutation
    public void removeParticipant(@InputArgument Integer id) {
        inMemoryDB.removeParticipantById(Long.valueOf(id));
    }
}
