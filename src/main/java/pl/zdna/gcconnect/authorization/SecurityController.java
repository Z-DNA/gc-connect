package pl.zdna.gcconnect.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SecurityController {
    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping(value = "/principal")
    public String securePrincipal(Principal principal) {
        return "Hello %s".formatted(principal.getName());
    }
}
