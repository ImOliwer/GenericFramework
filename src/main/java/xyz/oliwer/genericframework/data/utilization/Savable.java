package xyz.oliwer.genericframework.data.utilization;

/**
 * This interface represents a saver (AKA savable object).
 *
 * @param <Item> the type of item to save.
 * @param <By> the type of object needed to reference the save.
 */
public interface Savable<Item, By> {
    /**
     * Save an item.
     *
     * @param by {@link By} the object to reference said item by - lookup in most cases.
     */
    void save(Item item, By by);

    /**
     * Save an item asynchronously.
     *
     * @see Savable#save(Object, Object)
     */
    void saveAsync(Item item, By by);

    /**
     * Save all items.
     * <p>
     * <b>NOTE: This function is not supported by all implementations.</b>
     *
     * @throws UnsupportedOperationException if the functionality is not supported in said implementation.
     */
    default void saveAll() {
        throw new UnsupportedOperationException("saveAll is not supported in this implementation");
    }

    /**
     * Save all items asynchronously.
     *
     * @throws UnsupportedOperationException if the functionality is not supported in said implementation.
     */
    default void saveAllAsync() {
        throw new UnsupportedOperationException("saveAllAsync is not supported in this implementation");
    }
}