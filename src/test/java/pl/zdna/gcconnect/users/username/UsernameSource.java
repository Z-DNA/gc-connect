package pl.zdna.gcconnect.users.username;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(UsernameArgumentProvider.class)
public @interface UsernameSource {
    boolean VALID = true;
    boolean INVALID = false;

    int size();

    boolean status() default VALID;
}
