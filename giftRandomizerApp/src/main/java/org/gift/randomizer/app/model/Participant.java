package org.gift.randomizer.app.model;

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
