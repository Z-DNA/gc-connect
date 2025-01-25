package pl.zdna.gcconnect.users.username;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class PropertyLoader {
    public static String[] getPropertyValues(String propertyName) throws IOException {
        ClassPathResource resource = new ClassPathResource("application.properties");
        ResourcePropertySource propertySource = new ResourcePropertySource(resource);

        String propertyValue = (String) propertySource.getProperty(propertyName);
        return propertyValue != null ? propertyValue.split("\\s*,\\s*") : new String[0];
    }
}
