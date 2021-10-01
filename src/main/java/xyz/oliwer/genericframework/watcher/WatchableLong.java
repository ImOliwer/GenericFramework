package xyz.oliwer.genericframework.watcher;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable long.
 */
public final class WatchableLong extends Watchable<Long> implements WatchableNumber<Long> {
    /**
     * {@link Long} the value of this watchable.
     */
    private long value;

    /**
     * @param initial {@link Long} the initial value for this watchable.
     */
    WatchableLong(long initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Long value) {
        if (value == null) {
            return;
        }

        final long oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Long toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> action == INCREMENT ? value + toCrement : value - toCrement
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Long get() {
        return this.value;
    }
}