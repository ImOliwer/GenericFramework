package xyz.oliwer.genericframework.util;

/**
 * This interface represents a type that can be unwrapped into another.
 *
 * @param <Type> the type to unwrap to.
 */
@FunctionalInterface
public interface Wrappable<Type> {
    /**
     * @return {@link Type} unwrapped instance.
     */
    Type unwrap();
}