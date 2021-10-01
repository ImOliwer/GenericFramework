package xyz.oliwer.genericframework.service;

import org.jetbrains.annotations.Nullable;
import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor;
import xyz.oliwer.genericframework.service.global.PlaceholderService;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents implementation of {@link PlaceholderService}.
 */
final class PlaceholderServiceImpl implements PlaceholderService {
    /**
     * {@link Map} this map represents all classes & associated processors.
     */
    private final Map<Class<?>, PlaceholderProcessor<?>> registered = new HashMap<>();

    /**
     * @see Service
     */
    PlaceholderServiceImpl() {}

    /**
     * Attach a new class and it's associated processor.
     *
     * @param holder {@link Class<Provider>} the holder for this processor.
     * @param processor {@link PlaceholderProcessor<Provider>} the processor to attach.
     * @return {@link Boolean} whether it was attached successfully (false if there already is one with the same holder).
     */
    @Override
    public synchronized <Provider> boolean attach(
        Class<Provider> holder,
        PlaceholderProcessor<Provider> processor
    ) {
        return registered.putIfAbsent(holder, processor) == null;
    }

    /**
     * Detach a processor by its holder.
     *
     * @param holder {@link Class<Provider>} the holder of said processor to detach.
     * @return {@link PlaceholderProcessor<Provider>} processor of which was detached, null if none.
     */
    @Override
    public synchronized @Nullable <Provider> PlaceholderProcessor<Provider> detach(Class<Provider> holder) {
        return (PlaceholderProcessor<Provider>) registered.remove(holder);
    }

    /**
     * Get a processor by its holder.
     *
     * @param holder {@link Class<Provider>} the holder of said processor to fetch.
     * @return {@link PlaceholderProcessor<Provider>} processor of which was fetched, null if absent.
     */
    @Override
    public synchronized @Nullable <Provider> PlaceholderProcessor<Provider> get(Class<Provider> holder) {
        return (PlaceholderProcessor<Provider>) registered.get(holder);
    }
}