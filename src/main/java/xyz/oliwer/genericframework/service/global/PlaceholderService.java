package xyz.oliwer.genericframework.service.global;

import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor;

/**
 * This interface represents the service for placeholder processors.
 */
public interface PlaceholderService {
    /**
     * Attach a new class and it's associated processor.
     *
     * @param holder {@link Class<Provider>} the holder for this processor.
     * @param processor {@link PlaceholderProcessor<Provider>} the processor to attach.
     * @return {@link Boolean} whether it was attached successfully (false if there already is one with the same holder).
     */
    <Provider> boolean attach(Class<Provider> holder, PlaceholderProcessor<Provider> processor);

    /**
     * Detach a processor by its holder.
     *
     * @param holder {@link Class<Provider>} the holder of said processor to detach.
     * @return {@link PlaceholderProcessor<Provider>} processor of which was detached, null if none.
     */
    <Provider> PlaceholderProcessor<Provider> detach(Class<Provider> holder);

    /**
     * Get a processor by its holder.
     *
     * @param holder {@link Class<Provider>} the holder of said processor to fetch.
     * @return {@link PlaceholderProcessor<Provider>} processor of which was fetched, null if absent.
     */
    <Provider> PlaceholderProcessor<Provider> get(Class<Provider> holder);
}