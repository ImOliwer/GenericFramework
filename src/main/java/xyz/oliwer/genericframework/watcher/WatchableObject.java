package xyz.oliwer.genericframework.watcher;

/**
 * This class represents a thread-safe & mutable object that is being watched.
 *
 * @param <Item> the type of item to watch.
 */
public class WatchableObject<Item> extends Watchable<Item> {
    /**
     * {@link Item} current value of this watchable.
     */
    protected volatile Item value;

    /**
     * @see Watchables#watch(Object)
     * @param initial {@link Item} the initial value to be set.
     */
    public WatchableObject(Item initial) {
        this.value = initial;
    }

    /**
     * Update the value of this watchable object.
     *
     * @param value {@link Item} the object to be updated to.
     */
    public synchronized void update(Item value) {
        final Item oldValue = this.value;
        this.value = value;
        dispatchEvents(oldValue, value);
    }

    /**
     * @return {@link Item} current value of this watchable object.
     */
    @Override public synchronized Item get() {
        return this.value;
    }
}