package xyz.oliwer.genericframework.event;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation represents a type that acts as an event provider.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EventProvider {
    /**
     * @return {@link String} the name of this provider.
     */
    @NotNull
    String name();
}