package pl.zdna.gcconnect.users.domain;

public record Username(String username) {
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Username otherUsername)) {
            return false;
        } else {
            return username.equalsIgnoreCase(otherUsername.username);
        }
    }

    @Override
    public int hashCode() {
        return username.toLowerCase().hashCode();
    }
}
