package xyz.oliwer.genericframework.event;

import xyz.oliwer.genericframework.data.tuple.Tuple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static xyz.oliwer.genericframework.event.EventContext.CLASS_PROCESSOR;

/**
 * This package-private class represents the simple implementation of {@link EventRegistry}.
 */
final class SimpleEventRegistry implements EventRegistry {
    static final class RegisteredTrigger<Context extends EventContext<?>> {
        private final Class<Context> context;
        private final Trigger<Context> trigger;

        public RegisteredTrigger(
            Class<Context> context,
            Trigger<Context> trigger
        ) {
            this.context = context;
            this.trigger = trigger;
        }
    }

    private static SimpleEventRegistry instance;

    private final Map<Class<?>, List<RegisteredTrigger<? extends EventContext<?>>>> registry = new HashMap<>();

    @Override
    public boolean attachProvider(Class<?> providerClass) {
        final EventProvider provider = providerClass.getAnnotation(EventProvider.class);
        if (provider == null) {
            throw new IllegalStateException("Passed class is not an event provider");
        }
        return registry.putIfAbsent(providerClass, new LinkedList<>()) == null;
    }

    @Override
    public boolean detachProvider(Class<?> providerClass) {
        return registry.remove(providerClass) != null;
    }

    @Override
    public <Source, Context extends EventContext<Source>> void listen(
        Class<?> providerClass,
        Class<Context> contextClass,
        Trigger<Context> trigger
    ) {
        final EventProvider provider = providerClass.getAnnotation(EventProvider.class);
        if (provider == null) {
            throw new IllegalStateException("Passed class is not an event provider.");
        }

        final ContextInformation context = checkContext(contextClass);
        if (context.provider() != providerClass) {
            throw new IllegalStateException("Context provider and passed does not match.");
        }

        final List<RegisteredTrigger<? extends EventContext<?>>> triggers = registry.get(providerClass);
        triggers.add(new RegisteredTrigger<>(contextClass, trigger));
    }

    @Override
    public <Source, Context extends EventContext<Source>> void call(
        Class<Context> contextClass,
        Context context
    ) {
        final ContextInformation information = checkContext(contextClass);
        final Class<?> provider = information.provider();
        final List<RegisteredTrigger<? extends EventContext<?>>> registered = registry.get(provider);

        if (registered == null) {
            throw new IllegalStateException(CLASS_PROCESSOR.process(
                provider,
                "Context class {name} does not have a provider."
            ));
        }

        for (RegisteredTrigger<? extends EventContext<?>> it : registered) {
            if (it.context != contextClass) {
                continue;
            }
            final RegisteredTrigger<Context> registeredTrigger = (RegisteredTrigger<Context>) it;
            registeredTrigger.trigger.call(context);
        }
    }

    private static <Source> ContextInformation checkContext(Class<? extends EventContext<Source>> clazz) {
        final ContextInformation context = clazz.getAnnotation(ContextInformation.class);
        if (context == null) {
            throw new IllegalStateException("Information is missing from context.");
        }
        return context;
    }

    public static SimpleEventRegistry get() {
        if (instance == null) {
            instance = new SimpleEventRegistry();
        }
        return instance;
    }

    // Attach default providers.
    {
        attachProvider(Tuple.Mutable.class);
    }
}