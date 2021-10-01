package xyz.oliwer.genericframework.util;

import java.util.function.UnaryOperator;

import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction;

/**
 * This class represents utility functionality for number related operations.
 */
public final class NumberUtil {
    /** No instantiation. **/
    private NumberUtil() {
        throw new RuntimeException("Cannot instantiate NumberUtil");
    }

    /**
     * Get the min, max or sum of a cremental operation.
     *
     * @param current {@link N} the current value to crement.
     * @param min {@link N} minimum value possible.
     * @param max {@link N} maximum value possible.
     * @param action {@link CrementAction} the action that was handled.
     * @param operation {@link UnaryOperator<N>} the operation of crementing a number by current.
     * @param <N> type of <b>number</b> ({@link Number}) to check on.
     * @return {@link N} new, "updated" value after the cremental operation that was performed and checked min & max.
     */
    public static <N extends Number & Comparable<N>> N minMaxOf(
        N current, N min, N max,
        CrementAction action,
        UnaryOperator<N> operation
    ) {
        final N update = operation.apply(current);
        switch (action) {
            case INCREMENT: if (update.equals(min))
                return max;
            case DECREMENT: if (update.equals(max))
                return min;
        }
        return update;
    }
}