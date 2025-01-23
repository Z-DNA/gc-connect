package pl.zdna.gcconnect.users.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "okta.oauth2")
public record Auth0Data(String domain, String clientId, String clientSecret, String issuer, String tokenUrl,
                        String apiUrl, String connection) {
}

