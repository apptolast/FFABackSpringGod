package com.ffa.back.enums;

public enum MovieGroupStatus {

    NOT_IN_GROUP(false, null, false, null),        // La película no está en el grupo
    WATCHED_BY_OTHER(true, false, false, null),    // Otro usuario la marcó como vista
    WATCHED_BY_USER(true, false, true, false),     // Este usuario la marcó como vista
    TO_WATCH_BY_OTHER(true, true, false, null),    // Otro usuario la puso en "por ver"
    TO_WATCH_BY_USER(true, true, true, true);      // Este usuario la puso en "por ver"

    private final boolean hasMovie;
    private final Boolean toWatch;
    private final boolean addedByUser;
    private final Boolean userToWatchStatus;

    MovieGroupStatus(boolean hasMovie, Boolean toWatch, boolean addedByUser, Boolean userToWatchStatus) {
        this.hasMovie = hasMovie;
        this.toWatch = toWatch;
        this.addedByUser = addedByUser;
        this.userToWatchStatus = userToWatchStatus;
    }

    public boolean isHasMovie() {
        return hasMovie;
    }

    public Boolean getToWatch() {
        return toWatch;
    }

    public boolean isAddedByUser() {
        return addedByUser;
    }

    public Boolean getUserToWatchStatus() {
        return userToWatchStatus;
    }
}
