package pl.zdna.gcconnect.users.presentation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.zdna.gcconnect.vgn.VGNFactory;
import pl.zdna.gcconnect.vgn.VGNType;

@Log4j2
@Component
@RequiredArgsConstructor
public class InviteUserFormValidator implements Validator {
    private final VGNFactory vgnFactory;

    @Override
    public boolean supports(final @NonNull Class<?> clazz) {
        return InviteUserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final @NonNull Object target, final @NonNull Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                InviteUserForm.Fields.phoneNumber,
                "phoneNumber.invalid",
                "phone number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, InviteUserForm.Fields.username, "username.invalid", "username is required");

        final var inviteUserForm = (InviteUserForm) target;

        if (vgnFactory.isNotValid(VGNType.PHONE_NUMBER, inviteUserForm.getPhoneNumber())) {
            errors.rejectValue(
                    InviteUserForm.Fields.phoneNumber,
                    "phoneNumber.invalid",
                    "phone number is not a valid number");
        }

        if (vgnFactory.isNotValid(VGNType.USERNAME, inviteUserForm.getUsername())) {
            errors.rejectValue(
                    InviteUserForm.Fields.username,
                    "username.invalid",
                    "this username is not valid");
        }
    }
}
