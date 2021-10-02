package xyz.oliwer.genericframework.builder;

import xyz.oliwer.genericframework.util.Wrappable;

/**
 * This abstraction layer represents the builder for {@link Wrappable<Object>}.
 *
 * @param <Source> the source to build.
 * @param <W> the wrapper to build source <b>from</b>.
 */
public abstract class WrappableAbstractBuilder<Source, W extends Wrappable<Source>> extends AbstractBuilder<W, Source> {
    /**
     * Primary constructor;
     *
     * @param wrapper {@link W} the wrapper to initialize.
     */
    protected WrappableAbstractBuilder(W wrapper) {
        super(true);
        if (wrapper != null) {
            late.initialize(wrapper);
        }
    }

    /**
     * No-arg constructor.
     */
    protected WrappableAbstractBuilder() {
        this(null);
    }

    /**
     * Apply the wrapper for this builder.
     *
     * @param wrapper {@link W} the wrapper to be set.
     * @return {@link WrappableAbstractBuilder} current instance.
     */
    public WrappableAbstractBuilder<Source, W> withWrapper(W wrapper) {
        late.initialize(wrapper);
        return this;
    }

    /**
     * Wrap up and return the final built source.
     *
     * @return {@link Source} modified source.
     * @throws RuntimeException if the wrapper hasn't been applied.
     */
    @Override
    public final Source build() {
        return applyMeta(late.getSource()).unwrap();
    }
}