package org.sentiff.gift.randomizer.utils;

public enum ContentType {
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN("text/plain");

    public final String value;

    ContentType(String value) {
        this.value = value;
    }
}

