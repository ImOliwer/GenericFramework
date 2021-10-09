package xyz.oliwer.genericframework.data.storage;

import org.jetbrains.annotations.NotNull;
import xyz.oliwer.genericframework.data.utilization.Loadable;
import xyz.oliwer.genericframework.data.utilization.Savable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * This abstraction layer covers the <b>base structure</b> of every storage.
 *
 * @param <Source> type of source to load & save.
 * @param <By> type of object needed for referencing location of item etc.
 */
public abstract class AbstractStorage<Source, By> implements Loadable<Source, By>, Savable<Source, By> {
    /** {@link Executor} this property holds the executor for asynchronous executions. **/
    protected final Executor executor;

    /**
     * @param executor {@link Executor} the executor used for asynchronous executions.
     */
    protected AbstractStorage(Executor executor) {
        this.executor = executor;
    }

    /**
     * Load an item asynchronously.
     * @see Loadable#loadAsync(Object)
     */
    @Override
    public @NotNull CompletableFuture<Source> loadAsync(By by) {
        final CompletableFuture<Source> future = new CompletableFuture<>();
        executor.execute(() -> {
            final Source loaded = this.load(by);
            future.complete(loaded);
        });
        return future;
    }

    /**
     * Save an item asynchronously.
     * @see Savable#saveAsync(Object, Object)
     */
    @Override
    public void saveAsync(Source source, By by) {
        executor.execute(() -> save(source, by));
    }
}