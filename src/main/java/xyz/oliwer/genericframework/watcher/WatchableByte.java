package xyz.oliwer.genericframework.watcher;

import static java.lang.Byte.MAX_VALUE;
import static java.lang.Byte.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable byte.
 */
public final class WatchableByte extends Watchable<Byte> implements WatchableNumber<Byte> {
    /**
     * {@link Byte} the value of this watchable.
     */
    private byte value;

    /**
     * @param initial {@link Byte} the initial value for this watchable.
     */
    WatchableByte(byte initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Byte value) {
        if (value == null) {
            return;
        }

        final byte oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Byte toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> (byte) (action == INCREMENT ? value + toCrement : value - toCrement)
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Byte get() {
        return this.value;
    }
}