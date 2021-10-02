package xyz.oliwer.genericframework.builder;

/**
 * This interface represents an item that can be built.
 *
 * @param <Source> type of source.
 */
@FunctionalInterface
public interface Buildable<Source> {
    /**
     * Build corresponding instance of {@link Source}.
     *
     * @return {@link Source}
     */
    Source build();
}