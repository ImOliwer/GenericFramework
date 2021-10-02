package xyz.oliwer.genericframework.builder;

import xyz.oliwer.genericframework.util.LateInitialization;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * This abstraction layer covers the base of every abstract builder.
 *
 * @param <Source> the type of source.
 * @param <B> the type of build.
 */
public abstract class AbstractBuilder<Source, B> implements Buildable<B> {
    /**
     * {@link Queue<Consumer>} this queue holds the forwarded meta modifications.
     */
    protected final Queue<Consumer<Source>> metaQueue;

    /**
     * {@link LateInitialization<Source>} this late instance covers the initial source (if any).
     */
    protected final LateInitialization<Source> late;

    /**
     * Primary & no-arg constructor.
     */
    AbstractBuilder(boolean useLate) {
        this.metaQueue = new LinkedList<>();
        this.late = useLate ? LateInitialization.empty() : null;
    }

    /**
     * Push a modification to said builder.
     *
     * @param modification {@link Consumer<Source>} the modification to be pushed.
     * @return {@link SingleAbstractBuilder<Source>} current instance.
     */
    protected final AbstractBuilder<Source, B> push(Consumer<Source> modification) {
        metaQueue.add(modification);
        return this;
    }

    /**
     * Apply the meta to a source.
     *
     * @param source {@link Source} the source to apply to.
     * @return {@link Source} instance that was passed after modifications.
     */
    protected final Source applyMeta(Source source) {
        Consumer<Source> metaModification;
        while ((metaModification = metaQueue.poll()) != null) {
            metaModification.accept(source);
        }
        return source;
    }
}