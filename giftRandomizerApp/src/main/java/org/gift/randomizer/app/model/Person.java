package org.gift.randomizer.app.model;

import lombok.Value;
import org.gift.randomizer.app.model.GiftIdea;

import java.util.List;

@Value
public class Person {
    Long id;
    String name;
    List<GiftIdea> giftIdeas;
}
