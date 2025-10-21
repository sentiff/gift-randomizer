package org.sentiff.gift.randomizer.commons.storage;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class ParticipantRepositoryTest {

    private final ParticipantRepository participantRepository = spy(ParticipantRepository.class);

    @Test
    void testDescribe() {
        val expectedDescription = "interface: ParticipantRepository";
        val actualDescription = participantRepository.describe();
        assertEquals(expectedDescription, actualDescription);
    }
}
