package xyz.oliwer.genericframework.watcher;

import static java.lang.Float.MAX_VALUE;
import static java.lang.Float.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable float.
 */
public final class WatchableFloat extends Watchable<Float> implements WatchableNumber<Float> {
    /**
     * {@link Float} the value of this watchable.
     */
    private float value;

    /**
     * @param initial {@link Float} the initial value for this watchable.
     */
    WatchableFloat(float initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Float value) {
        if (value == null) {
            return;
        }

        final float oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Float toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> action == INCREMENT ? value + toCrement : value - toCrement
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Float get() {
        return this.value;
    }
}