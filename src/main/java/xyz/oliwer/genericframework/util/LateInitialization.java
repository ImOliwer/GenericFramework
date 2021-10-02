package xyz.oliwer.genericframework.util;

import static java.lang.String.format;

/**
 * This class represents a value that is initialized on a late basis.
 *
 * @param <Source> the source for this late initialization.
 */
public final class LateInitialization<Source> {
    /**
     * This {@link RuntimeException} implementation is only thrown
     * for {@link LateInitialization} related "issues".
     */
    private static final class Exception extends RuntimeException {
        Exception(String key) {
            super(format("Source has %s been initialized", key));
        }
    }

    /**
     * {@link Source} the source instance.
     */
    private Source source;

    /**
     * {@link Boolean} whether this source has been initialized.
     */
    private boolean initialized;

    /**
     * No need for this;
     */
    private LateInitialization() {}

    /**
     * Initialize the source in said instance.
     *
     * @param source {@link Source} the source to be assigned.
     * @throws Exception if the source already has been initialized.
     */
    public void initialize(Source source) {
        if (source == null) {
            return;
        }

        if (initialized) {
            throw new Exception("already");
        }

        this.source = source;
        this.initialized = true;
    }

    /**
     * Get the source of this late initialization.
     *
     * @return {@link Source}
     * @throws Exception if the source has not yet been initialized.
     */
    public Source getSource() {
        if (!this.initialized) {
            throw new Exception("not");
        }
        return this.source;
    }

    /**
     * Get whether the source of this instance has been initialized.
     *
     * @return {@link Boolean}
     */
    public boolean isInitialized() {
        return this.initialized;
    }

    /**
     * Create a new and empty instance.
     *
     * @return {@link LateInitialization<S>}
     */
    public static <S> LateInitialization<S> empty() {
        return new LateInitialization<>();
    }
}