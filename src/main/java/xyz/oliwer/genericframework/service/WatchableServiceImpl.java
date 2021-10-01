package xyz.oliwer.genericframework.service;

import org.jetbrains.annotations.Nullable;
import xyz.oliwer.genericframework.service.global.WatchableService;
import xyz.oliwer.genericframework.watcher.Watchable;

import java.util.Map;
import java.util.function.Function;

/**
 * This class represents the implementation of {@link WatchableService}.
 */
final class WatchableServiceImpl implements WatchableService {
    /**
     * {@link Map} this map contains all default watchables.
     */
    private final Map<Class<?>, Function<Object, Object>> watchables =
        Watchable.defaults();

    /**
     * Create a new watchable by type and initial value.
     */
    @Override
    public <Item, Type extends Watchable<Item>> @Nullable Type watch(Class<?> type, Item initial) {
        final Function<Object, Object> creator = watchables.get(type);

        if (creator == null) {
            return null;
        }

        return (Type) creator.apply(initial);
    }
}