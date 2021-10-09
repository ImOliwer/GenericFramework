package xyz.oliwer.genericframework.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class represents util functionality for consumers.
 */
public final class Consumers {
    /** No instantiation needed. **/
    private Consumers() {
        throw new RuntimeException("Cannot instantiate ConsumerUtil");
    }

    /**
     * Consume and transform an instance by passed down object & options.
     *
     * @param instance {@link I} the initial instance to consume and transform.
     * @param modifications {@link Consumer<I>} array of modifications to apply to passed instance.
     * @param transform {@link Function} function to call after modifications have been applied - for a return instance.
     * @param <I> type of instance.
     * @param <R> type of returnable.
     * @return {@link R} returned after 'then' invocation.
     */
    public static <I, R> R consumeAndTransform(I instance, Consumer<I>[] modifications, Function<I, R> transform) {
        for (Consumer<I> modification : modifications)
            modification.accept(instance);
        return transform.apply(instance);
    }
}