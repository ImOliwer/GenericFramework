package xyz.oliwer.genericframework.event;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation presents information about a type of context.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextInformation {
    /**
     * @return {@link Class} the provider of relative context.
     */
    @NotNull
    Class<?> provider();
}