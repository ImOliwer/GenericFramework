package xyz.oliwer.genericframework.watcher;

import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

/**
 * This class represents a thread-safe, mutable (not direct) & watched string.
 */
public final class WatchableString extends WatchableObject<String> {
    /**
     * {@link Pattern} pattern for single numbers.
     *
     * <b>Results;</b>
     * - {@link Long}
     * - {@link Integer}
     * - {@link Short}
     * - {@link Byte}
     */
    private static final Pattern SINGLE_NUMBER_PATTERN  = compile("^-?\\d+$");

    /**
     * {@link Pattern} pattern for decimal numbers.
     *
     * <b>Results;</b>
     * - {@link Double}
     * - {@link Float}
     */
    private static final Pattern DECIMAL_NUMBER_PATTERN = compile("^(-?)(\\d+)?(\\.)?(\\d+)$");

    /**
     * @param initial {@link String} the initial string to be set.
     * 'Watchables.watch(String.class, "initial")'
     */
    WatchableString(String initial) {
        super(initial);
    }

    // TODO; add more utility functions

    /**
     * Format and update the value by passed down string & arguments.
     *
     * @param value {@link String} value to format then update.
     * @param arguments {@link Object} array of objects to format by passed down value.
     */
    public synchronized void formatAndUpdate(String value, Object... arguments) {
        update(value == null ? null : format(value, arguments));
    }

    /**
     * Get whether the value of this watchable string can be parsed into a number type or not.
     *
     * @param type {@link Class} the number type to check.
     * @return {@link Boolean} whether parsing is an option for said type.
     */
    public boolean canParseTo(Class<? extends Number> type) {
        if (value == null) {
            return false;
        }

        if (type == long.class || type == int.class || type == short.class || type == byte.class) {
            return SINGLE_NUMBER_PATTERN.matcher(value).matches();
        }

        return (type == double.class || type == float.class) && DECIMAL_NUMBER_PATTERN.matcher(value).matches();
    }

    /**
     * Attempt to parse the current string value in this watchable to a {@link Number} child type.
     *
     * @param type {@link Class<Type>} the type to attempt to parse into.
     * @param <Type> the number type to parse into.
     * @return {@link Type} parsed value.
     * @throws NumberFormatException if the value is not parsable to said type.
     */
    public <Type extends Number> Type parseTo(Class<Type> type) {
        // number result
        final Number result;

        // attempt parse of possible classes
        if (type == long.class || type == Long.class)
            result = Long.valueOf(value);
        else if (type == int.class || type == Integer.class)
            result = Integer.valueOf(value);
        else if (type == short.class || type == Short.class)
            result = Short.valueOf(value);
        else if (type == byte.class || type == Byte.class)
            result = Byte.valueOf(value);
        else if (type == double.class || type == Double.class)
            result = Double.valueOf(value);
        else if (type == float.class || type == Float.class)
            result = Float.valueOf(value);
        else result = null;

        // if the result is null, it could not perform said parse operation
        if (result == null) {
            throw new NumberFormatException(format(
                "Could not parse watchable string '%s' to '%s'",
                this.value, type.getSimpleName()
            ));
        }

        // send back the new result
        return (Type) result;
    }
}