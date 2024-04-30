package org.sentiff.gift.randomizer.commons.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Participant extends Person {

    private final List<GiftIdea> giftIdeas;

    public Participant(Long id, String name, List<GiftIdea> giftIdeas) {
        super(id, name);
        this.giftIdeas = giftIdeas;
    }
}
