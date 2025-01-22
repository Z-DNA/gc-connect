package pl.zdna.gcconnect.users.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.zdna.gcconnect.authorization.LoggedInUserService;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final LoggedInUserService loggedInUserService;

    @GetMapping(value = "/bug")
    public String bug(Model model) {
        //TODO: debug why on first login there is error 999; GCC-15
        final var principal = loggedInUserService.getLoggedInPrincipal();
        model.addAttribute("profile", principal.getClaims());
        return "index";
    }

    @GetMapping(value = "/")
    public String index(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser)
            model.addAttribute("profile", oidcUser.getClaims());

        return "index";
    }

    @GetMapping(value = "/invite")
    public String inviteView(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            model.addAttribute("profile", oidcUser.getClaims());
        }
        model.addAttribute("username", new Username());

        return "invite";
    }

    @PostMapping(value = "/invite")
    public String invite(@ModelAttribute Username username, Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            model.addAttribute("profile", oidcUser.getClaims());
        }
        model.addAttribute("username", username);
        return "invited";
    }
}

