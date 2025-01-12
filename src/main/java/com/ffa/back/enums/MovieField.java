package com.ffa.back.enums;

import lombok.Getter;

@Getter
public enum MovieField {
    TO_WATCH(true),
    WATCHED(false);

    private final boolean to_watch;


    MovieField(boolean toWatch) {
        this.to_watch = toWatch;
    }

}
