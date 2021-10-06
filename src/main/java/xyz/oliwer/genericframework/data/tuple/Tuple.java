package xyz.oliwer.genericframework.data.tuple;

import com.google.gson.reflect.TypeToken;
import xyz.oliwer.genericframework.data.tuple.identifier.TupleIdentifier;
import xyz.oliwer.genericframework.event.EventProvider;

/**
 * This interface is the base of every tuple implementation.
 *
 * @param <Identifier> type of identifier.
 */
@FunctionalInterface
public interface Tuple<Identifier extends TupleIdentifier> {
    /**
     * This interface represents a mutable tuple.
     *
     * @param <Identifier> type of identifier.
     */
    @EventProvider(name = "Tuple.Mutable")
    interface Mutable<Identifier extends TupleIdentifier> extends Tuple<Identifier> {
        /**
         * Set the value of passed down identifier.
         *
         * @param identifier {@link Identifier} the identifier of said property to modify.
         * @param value {@link Type} the value to assign said identifier to.
         */
        <Type> void set(Identifier identifier, Type value);

        /**
         * Fetch class of {@link Mutable} containing generic type of identifier.
         *
         * @param <I> the identifier for said mutable.
         * @return {@link Class<Mutable>}
         */
        @SuppressWarnings("unchecked")
        static <I extends TupleIdentifier> Class<Mutable<I>> accessor() {
            return (Class<Mutable<I>>) new TypeToken<Mutable<I>>(){}.getRawType();
        }
    }

    /**
     * Fetch value accordingly by passed down identifier.
     *
     * @param identifier {@link Identifier} identifier of said value to fetch.
     * @return {@link Type} corresponding value.
     */
    <Type> Type get(Identifier identifier);

    /**
     * Enforce the binding of non-nullable identifiers.
     *
     * @param identifier {@link Identifier} the identifier to check.
     */
    static <Identifier extends TupleIdentifier> void checkNotNull(Identifier identifier) {
        if (identifier == null)
            throw new IllegalStateException("Identifier must not be null");
    }
}