package pl.zdna.gcconnect.users.domain;

public enum UserAccountStatus {
    INVITED,
    ACTIVE,
    EXPIRED,
    DELETED;

    public static UserAccountStatus newUserStatus() {
        return INVITED;
    }

    public boolean isNewUserStatus() {
        return this == UserAccountStatus.newUserStatus();
    }
}
