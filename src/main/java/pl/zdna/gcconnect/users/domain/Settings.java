package pl.zdna.gcconnect.users.domain;

import lombok.NonNull;

public record Settings(@NonNull Privacy privacy, @NonNull ReactivationPolicy reactivationPolicy) {
    public static Settings getDefault() {
        return new Settings(Privacy.getDefault(), ReactivationPolicy.getDefault());
    }

    public Settings withPrivacy(final Privacy newPrivacy) {
        return new Settings(newPrivacy, this.reactivationPolicy);
    }

    public Settings withReactivationPolicy(final ReactivationPolicy newPolicy) {
        return new Settings(this.privacy, newPolicy);
    }
}
