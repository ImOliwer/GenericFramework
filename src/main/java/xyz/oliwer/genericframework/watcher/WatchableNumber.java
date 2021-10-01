package xyz.oliwer.genericframework.watcher;

/**
 * This interface represents a watchable number.
 *
 * @param <Type> the type of number.
 */
public interface WatchableNumber<Type extends Number> {
    /**
     * This enumeration holds the available crementals.
     */
    enum CrementAction {
        INCREMENT,
        DECREMENT
    }

    /**
     * Crement the current value by passed down.
     *
     * @param action {@link CrementAction} action to perform on current value.
     * @param toCrement {@link Type} value to be modified alongside current.
     */
    void crement(CrementAction action, Type toCrement);
}