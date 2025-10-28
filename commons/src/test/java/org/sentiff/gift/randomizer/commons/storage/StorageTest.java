package org.sentiff.gift.randomizer.commons.storage;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class StorageTest {

    private final Storage storage = spy(Storage.class);

    @Test
    void testDescribe() {
        val expectedDescription = "interface: Storage, currently extends: [ParticipantRepository, ObservationRepository]";
        val actualDescription = storage.describe();
        assertEquals(expectedDescription, actualDescription);
    }
}