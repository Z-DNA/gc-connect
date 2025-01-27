package pl.zdna.gcconnect.users.infrastructure.user;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import pl.zdna.gcconnect.users.domain.Privacy;
import pl.zdna.gcconnect.users.domain.ReactivationPolicy;
import pl.zdna.gcconnect.users.domain.UserAccountStatus;

import java.time.Instant;

@Data
@FieldNameConstants
@Document(collection = "users")
public class UserEntity {
    @Id private String id;

    @Field
    @Indexed(unique = true)
    private String username;

    private String phoneNumber;
    private String inviterUsername;
    private UserAccountStatus status;

    private Instant creationTimestamp;
    private Instant activationTimestamp;

    private String email;
    private Privacy privacy;
    private ReactivationPolicy reactivationPolicy;
}
