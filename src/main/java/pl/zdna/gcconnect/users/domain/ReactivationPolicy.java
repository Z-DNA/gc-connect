package pl.zdna.gcconnect.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReactivationPolicy {
    NEVER("No self-reactivation"),
    ASK("Ask to self-reactivate"),
    OPEN("Self-reactivate without asking");

    private final String policy;

    public static ReactivationPolicy getDefault() {
        return NEVER;
    }
}
