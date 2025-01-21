package pl.zdna.gcconnect.users.domain;

public enum Privacy {
    PUBLIC, PRIVATE, HIDDEN;

    public static Privacy getDefault(){
        return PRIVATE;
    }
}

