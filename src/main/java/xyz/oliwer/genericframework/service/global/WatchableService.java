package xyz.oliwer.genericframework.service.global;

import org.jetbrains.annotations.Nullable;
import xyz.oliwer.genericframework.watcher.Watchable;

/**
 * This functional interface represents the single function for
 * generating new instances of watchable objects & primitives.
 */
@FunctionalInterface
public interface WatchableService {
    /**
     * Watch a new "object" by passed down class and initial value.
     *
     * @return {@link Type} freshly created watchable with said value, if successfully created.
     */
    <Item, Type extends Watchable<Item>> @Nullable Type watch(Class<?> type, Item initial);
}