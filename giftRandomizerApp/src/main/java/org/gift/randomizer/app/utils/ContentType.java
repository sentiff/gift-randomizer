package org.gift.randomizer.app.utils;

public enum ContentType {
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN("text/plain");

    public final String value;

    ContentType(String value) {
        this.value = value;
    }
}

