package com.ffa.back.enums;

public enum MovieField {
    TO_WATCH(true),
    WATCHED(false);

    private final boolean to_watch;


    MovieField(boolean toWatch) {
        this.to_watch = toWatch;
    }

    public boolean isTo_watch() {
        return to_watch;
    }
}
