package pl.zdna.gcconnect.users.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.zdna.gcconnect.shared.Response;
import pl.zdna.gcconnect.vgn.VGNFactory;
import pl.zdna.gcconnect.users.application.UserService;
import pl.zdna.gcconnect.users.application.results.TemporaryUserCreatedResult;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final @Qualifier("inviteUserFormValidator") Validator inviteUserFormValidator;
    private final VGNFactory vgnFactory;

    @ModelAttribute
    public void addCommonAttributes(Model model, Authentication authentication){
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            model.addAttribute("profile", oidcUser.getClaims());
        }
    }

    @InitBinder(value = "inviteUserForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(inviteUserFormValidator);
    }

    @GetMapping(value = "/")
    public String index(Model model, Authentication authentication) {
        return "index";
    }

    @GetMapping(value = "/invite")
    public String inviteView(Model model) {
        model.addAttribute("inviteUserForm", new InviteUserForm());
        return "invite";
    }

    @PostMapping(value = "/invite")
    public String invite(Model model,
                         @Validated @ModelAttribute InviteUserForm inviteUserForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug("Validation error: {}", error.getDefaultMessage()));
            model.addAttribute("inviteUserForm", inviteUserForm);
            return "invite";
        }

        final var futureResponse = userService.createTemporaryUser(inviteUserForm.getUsername(), inviteUserForm.getPhoneNumber());
        handleResponseForUserInvitation(model, futureResponse.join());
        return "invited";
    }

    private void handleResponseForUserInvitation(final Model model, final Response response){
        if (response.isSuccess())
            handleSuccessResponseForUserInvitation(model, response.getResult());
        else
            handleFailedResponseForUserInvitation(model, response.getError());
    }

    private void handleSuccessResponseForUserInvitation(final Model model, final TemporaryUserCreatedResult result) {
        model.addAttribute("isInvitationSuccessful", true);
        model.addAttribute("invitedUser", result);
    }

    private void handleFailedResponseForUserInvitation(final Model model, final String error){
        model.addAttribute("isInvitationSuccessful", false);
        model.addAttribute("error", error);
    }
}

