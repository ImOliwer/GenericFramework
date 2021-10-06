package xyz.oliwer.genericframework.data.tuple.event;

import com.google.gson.reflect.TypeToken;
import xyz.oliwer.genericframework.data.tuple.Tuple;
import xyz.oliwer.genericframework.data.tuple.identifier.TupleIdentifier;
import xyz.oliwer.genericframework.event.ContextInformation;
import xyz.oliwer.genericframework.event.EventContext;

import static xyz.oliwer.genericframework.data.tuple.Tuple.Mutable;

/**
 * This class represents the "value modification" event context for {@link Tuple.Mutable}.
 *
 * @param <Identifier> type of identifier - child of {@link TupleIdentifier}.
 * @param <Source> type of source - child of {@link Tuple.Mutable}.
 */
@ContextInformation(provider = Tuple.Mutable.class)
public final class TupleModifiedContext<Identifier extends TupleIdentifier, Source extends Mutable<Identifier>> extends EventContext<Source> {
    /** {@link Identifier} the identifier of this context call. **/
    private final Identifier identifier;

    /** {@link Object} the previous object set in the involved mutable. **/
    private final Object previous;

    /** {@link Object} the updated object. **/
    private final Object updated;

    /** Primary constructor. **/
    public TupleModifiedContext(
        Source source,
        Identifier identifier,
        Object previous,
        Object updated
    ) {
        super(source);
        this.identifier = identifier;
        this.previous = previous;
        this.updated = updated;
    }

    /**
     * Get the identifier involved in this context.
     *
     * @return {@link Identifier}
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * Get the previous value.
     *
     * @return {@link Object}
     */
    public Object getPrevious() {
        return this.previous;
    }

    /**
     * Get the updated value.
     *
     * @return {@link Object}
     */
    public Object getUpdated() {
        return this.updated;
    }

    /**
     * Fetch class of {@link TupleModifiedContext} containing generic types.
     *
     * @param <I> the identifier for said mutable.
     * @param <M> the mutable tuple.
     * @return {@link Class<TupleModifiedContext>}
     */
    @SuppressWarnings("unchecked")
    public static <I extends TupleIdentifier, M extends Mutable<I>> Class<TupleModifiedContext<I, M>> accessor() {
        return (Class<TupleModifiedContext<I, M>>) new TypeToken<TupleModifiedContext<I, M>>(){}.getRawType();
    }
}