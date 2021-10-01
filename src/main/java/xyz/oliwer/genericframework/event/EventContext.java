package xyz.oliwer.genericframework.event;

import org.jetbrains.annotations.NotNull;
import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor;

import static xyz.oliwer.genericframework.placeholder.PlaceholderProcessor.byReplace;

/**
 * This abstraction layer represents the context of an event.
 */
public abstract class EventContext<Source> {
    /**
     * {@link PlaceholderProcessor<Class>} this property represents the placeholder processor for class.
     */
    public static final PlaceholderProcessor<Class<?>> CLASS_PROCESSOR = byReplace(data ->
        data.append("{name}", Class::getSimpleName)
    );

    /**
     * {@link Source} the relative source of this event context.
     */
    private final Source source;

    /**
     * Primary constructor.
     */
    public EventContext(Source source) {
        this.source = source;
    }

    /**
     * Get the source of this context.
     *
     * @return {@link Source}
     */
    public Source getSource() {
        return this.source;
    }

    /**
     * Get the provider of this context.
     *
     * @return {@link Class}
     */
    public final @NotNull Class<?> getProvider() {
        final Class<? extends EventContext<?>> contextClass = (Class<? extends EventContext<?>>) this.getClass();
        final ContextInformation information = contextClass.getAnnotation(ContextInformation.class);

        if (information == null) {
            throw new IllegalStateException(CLASS_PROCESSOR.process(
                contextClass,
                "Missing context information from type {name}."
            ));
        }
        return information.provider();
    }
}