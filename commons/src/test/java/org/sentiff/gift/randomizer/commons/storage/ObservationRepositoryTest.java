package org.sentiff.gift.randomizer.commons.storage;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class ObservationRepositoryTest {
    private final ObservationRepository observationRepository = spy(ObservationRepository.class);

    @Test
    void testDescribe() {
        val expectedDescription = "interface: ObservationRepository";
        val actualDescription = observationRepository.describe();
        assertEquals(expectedDescription, actualDescription);
    }
}
