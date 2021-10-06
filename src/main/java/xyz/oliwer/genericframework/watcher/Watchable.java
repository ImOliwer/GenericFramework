package xyz.oliwer.genericframework.watcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class represents the watchable base.
 *
 * @param <Item> the type of item to watch.
 */
public abstract class Watchable<Item> implements Supplier<Item> {
    /**
     * This interface represents the single function that
     * is called when a watchable is updated - AKA an event "handler".
     *
     * @param <Item> the type of item being changed.
     */
    @FunctionalInterface
    public interface Event<Item> {
        void onChange(Item oldValue, Item newValue);
    }

    /**
     * {@link List<Event>} list of all events to be triggered upon
     */
    private final List<Event<Item>> events = new LinkedList<>();

    /**
     * Update this watchable.
     *
     * @param value {@link Item} the new value to be updated to.
     */
    public abstract void update(Item value);

    /**
     * Attach a new event to this watchable.
     *
     * @param event {@link Event<Item>} the new event to attach.
     * @return {@link Watchable<Item>} current instance.
     */
    public Watchable<Item> attach(Event<Item> event) {
        events.add(event);
        return this;
    }

    /**
     * @see Watchable#attach(Event)
     * @param supplier {@link Supplier<Event>} the supplier to fetch event from.
     */
    public Watchable<Item> attach(Supplier<Event<Item>> supplier) {
        return attach(supplier.get());
    }

    /**
     * Detach an event from this watchable.
     *
     * @param event {@link Event<Item>} the event to detach.
     * @return {@link Boolean} whether passed event was detached or not.
     */
    public boolean detach(Event<Item> event) {
        return events.remove(event);
    }

    /**
     * Dispatch all registered events with old and updated values.
     *
     * @param oldValue {@link Item} previously assigned value.
     * @param newValue {@link Item} new value that was set.
     */
    protected synchronized void dispatchEvents(Item oldValue, Item newValue) {
        for (Event<Item> event : this.events)
            event.onChange(oldValue, newValue);
    }

    /**
     * @return {@link Map} new map containing all watchable defaults.
     */
    public static Map<Class<?>, Function<Object, Object>> defaults() {
        final Map<Class<?>, Function<Object, Object>> defaults = new HashMap<>();

        defaults.put(String.class, item -> new WatchableString((String) item));
        defaults.put(long.class, item -> new WatchableLong((long) item));
        defaults.put(int.class, item -> new WatchableInteger((int) item));
        defaults.put(short.class, item -> new WatchableShort((short) item));
        defaults.put(byte.class, item -> new WatchableByte((byte) item));
        defaults.put(double.class, item -> new WatchableDouble((double) item));
        defaults.put(float.class, item -> new WatchableFloat((float) item));

        return defaults;
    }
}