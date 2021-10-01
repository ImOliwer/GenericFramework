package xyz.oliwer.genericframework.watcher;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static xyz.oliwer.genericframework.util.NumberUtil.minMaxOf;
import static xyz.oliwer.genericframework.watcher.WatchableNumber.CrementAction.INCREMENT;

/**
 * This class represents a thread-safe, mutable & watchable double.
 */
public final class WatchableDouble extends Watchable<Double> implements WatchableNumber<Double> {
    /**
     * {@link Double} the value of this watchable.
     */
    private double value;

    /**
     * @param initial {@link Double} the initial value for this watchable.
     */
    WatchableDouble(double initial) {
        this.value = initial;
    }

    /** Update the value. **/
    @Override
    public synchronized void update(Double value) {
        if (value == null) {
            return;
        }

        final double oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /** Crement the value. **/
    @Override
    public synchronized void crement(CrementAction action, Double toCrement) {
        update(minMaxOf(
            value, MIN_VALUE, MAX_VALUE, action,
            old -> action == INCREMENT ? value + toCrement : value - toCrement
        ));
    }

    /** Get the current value. **/
    @Override
    public synchronized Double get() {
        return this.value;
    }
}