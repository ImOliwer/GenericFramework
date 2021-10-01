package xyz.oliwer.genericframework.event;

/**
 * This interface represents a registry holding event providers & its context listeners.
 */
public interface EventRegistry {
    /**
     * This interface represents the single function to handle
     * a listener when said {@link Context} is forwarded from
     * corresponding {@link EventProvider}.
     *
     * @param <Context> type of context.
     */
    @FunctionalInterface
    interface Trigger<Context extends EventContext<?>> {
        void call(Context context);
    }

    /**
     * Attach a provider to this registry.
     *
     * @param providerClass {@link Class} class of the provider to attach.
     * @return {@link Boolean} whether attachment was successful.
     */
    boolean attachProvider(Class<?> providerClass);

    /**
     * Detach a provider from this registry.
     *
     * @param providerClass {@link Class} class of the provider to detach.
     * @return {@link Boolean} whether detachment was successful.
     */
    boolean detachProvider(Class<?> providerClass);

    /**
     * Listen to a context by passed provider and said context classes
     * alongside the trigger (listener) to invoke upon forward.
     *
     * @param providerClass {@link Class} class of the provider to add a listener to.
     * @param contextClass {@link Class} class of the context to listen to.
     * @param trigger {@link Trigger<Context>} the block to perform upon context listener forwarding.
     */
    <Source, Context extends EventContext<Source>> void listen(
        Class<?> providerClass,
        Class<Context> contextClass,
        Trigger<Context> trigger
    );

    /**
     * Call & forward a context by its class and instance.
     *
     * @param contextClass {@link Class<Context>} class of the context to call and forward.
     * @param context {@link Context} the instance of context that will be passed down to listeners.
     */
    <Source, Context extends EventContext<Source>> void call(
        Class<Context> contextClass,
        Context context
    );

    /**
     * @return {@link EventRegistry} the simple event registry instance.
     */
    static EventRegistry get() {
        return SimpleEventRegistry.get();
    }
}