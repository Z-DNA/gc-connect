package pl.zdna.gcconnect.users.infrastructure.temporaryuser;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.zdna.gcconnect.users.domain.UserAccountStatus;

import java.time.Instant;

@Data
@FieldNameConstants
@Document(collection = "temporary_users")
public class TemporaryUserEntity {
    @Id private String id;

    private String username;
    private String phoneNumber;
    private String inviterUsername;
    private UserAccountStatus status;

    private Instant creationTimestamp;
    private Instant activationTimestamp;
}
