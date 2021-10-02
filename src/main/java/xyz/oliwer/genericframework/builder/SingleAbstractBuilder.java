package xyz.oliwer.genericframework.builder;

/**
 * This abstraction layer covers & represents the "Single" builder.
 *
 * @param <Source> type of source to build.
 */
public abstract class SingleAbstractBuilder<Source> extends AbstractBuilder<Source, Source> {
    /**
     * Primary & no-arg constructor.
     */
    protected SingleAbstractBuilder() {
        super(true);
    }

    /**
     * Initialize an initial source if none already has been.
     *
     * @param initial {@link Source} the initial source to initialize.
     * @return {@link SingleAbstractBuilder<Source>} current instance.
     * @throws RuntimeException if this builder instance's initial source already has been initialized.
     */
    public final SingleAbstractBuilder<Source> withInitial(Source initial) {
        late.initialize(initial);
        return this;
    }

    /**
     * Prepare a new source instance.
     *
     * @return {@link Source}
     */
    protected abstract Source createInstance();

    /**
     * Wrap up and return the final built source.
     *
     * @return {@link Source}
     */
    @Override
    public Source build() {
        final Source source = late.isInitialized() ? late.getSource() : createInstance();
        if (source == null) {
            throw new RuntimeException("Source must not be null");
        }
        return applyMeta(source);
    }
}