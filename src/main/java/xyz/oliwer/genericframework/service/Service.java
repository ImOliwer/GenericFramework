package xyz.oliwer.genericframework.service;

import xyz.oliwer.genericframework.service.global.PlaceholderService;
import xyz.oliwer.genericframework.service.global.WatchableService;

/**
 * This class holds services that only require one instance.
 */
public final class Service {
    /**
     * {@link PlaceholderService} the service for all placeholder processors.
     */
    private static PlaceholderService placeholderService;

    /**
     * {@link WatchableService} the controller for watchables.
     */
    private static WatchableService watchableService;

    /**
     * No instantiation needed.
     */
    private Service() {
        throw new RuntimeException("Cannot instantiate 'Service'");
    }

    /**
     * @return {@link PlaceholderService}
     */
    public static PlaceholderService placeholder() {
        if (placeholderService == null) {
            placeholderService = new PlaceholderServiceImpl();
        }
        return placeholderService;
    }

    /**
     * @return {@link WatchableService}
     */
    public static WatchableService watchable() {
        if (watchableService == null) {
            watchableService = new WatchableServiceImpl();
        }
        return watchableService;
    }
}