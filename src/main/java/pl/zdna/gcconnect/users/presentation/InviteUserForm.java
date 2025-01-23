package pl.zdna.gcconnect.users.presentation;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class InviteUserForm {
    private String username;
    private String phoneNumber;
}
