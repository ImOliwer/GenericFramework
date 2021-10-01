package xyz.oliwer.genericframework.watcher;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable integer.
 */
public final class WatchableInteger extends Watchable<Integer> implements WatchableNumber<Integer> {
    /**
     * {@link Integer} the value of this watchable.
     */
    private int value;

    /**
     * @param initial {@link Integer} the initial value for this watchable.
     */
    WatchableInteger(int initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Integer value) {
        if (value == null) {
            return;
        }

        final int oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Integer toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> action == INCREMENT ? value + toCrement : value - toCrement
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Integer get() {
        return this.value;
    }
}