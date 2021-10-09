package xyz.oliwer.genericframework.data.utilization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface represents a loader (AKA loadable object).
 *
 * @param <Item> the type of item to load.
 * @param <By> the type of object needed to reference the load.
 */
public interface Loadable<Item, By> {
    /**
     * Load an item.
     *
     * @param by {@link By} the object to reference said item by - lookup in most cases.
     * @return {@link Item}
     */
    @Nullable Item load(By by);

    /**
     * Load an item asynchronously.
     * <p>
     * <b>NOTE: The item completed is nullable.</b>
     *
     * @see Loadable#load(Object)
     * @return {@link CompletableFuture<Item>}
     */
    @NotNull CompletableFuture<Item> loadAsync(By by);

    /**
     * Load all items.
     * <p>
     * <b>NOTE: This function is not supported by all implementations.</b>
     *
     * @return {@link List<Item>}
     * @throws UnsupportedOperationException if the functionality is not supported in said implementation.
     */
    default List<Item> loadAll() {
        throw new UnsupportedOperationException("loadAll is not supported in this implementation");
    }

    /**
     * Load all items asynchronously.
     *
     * @return {@link CompletableFuture<List>}
     * @throws UnsupportedOperationException if the functionality is not supported in said implementation.
     */
    default CompletableFuture<List<Item>> loadAllAsync() {
        throw new UnsupportedOperationException("loadAllAsync is not supported in this implementation");
    }
}