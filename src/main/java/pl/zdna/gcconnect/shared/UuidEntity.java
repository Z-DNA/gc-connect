package pl.zdna.gcconnect.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class UuidEntity extends Entity {
    protected UUID uuid = UUID.randomUUID();
}
