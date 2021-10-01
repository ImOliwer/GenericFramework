package xyz.oliwer.genericframework.watcher;

import static java.lang.Short.MAX_VALUE;
import static java.lang.Short.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable short.
 */
public final class WatchableShort extends Watchable<Short> implements WatchableNumber<Short> {
    /**
     * {@link Short} the value of this watchable.
     */
    private short value;

    /**
     * @param initial {@link Short} the initial value for this watchable.
     */
    WatchableShort(short initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Short value) {
        if (value == null) {
            return;
        }

        final short oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Short toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> (short) (action == INCREMENT ? value + toCrement : value - toCrement)
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Short get() {
        return this.value;
    }
}