package org.sentiff.gift.randomizer.commons.storage;

import lombok.val;

import java.util.Arrays;

public interface Storage extends ParticipantRepository, ObservationRepository {

    @Override
    default String describe() {
        val currentName = Storage.class.getSimpleName();
        val currentExtensions = Arrays.stream(Storage.class.getInterfaces()).map(Class::getSimpleName).toList();
        return String.format("interface: %s, currently extends: %s", currentName, currentExtensions);
    }
}
