package xyz.oliwer.genericframework.data.tuple.impl;

import xyz.oliwer.genericframework.data.tuple.Tuple;
import xyz.oliwer.genericframework.data.tuple.event.TupleModifiedContext;
import xyz.oliwer.genericframework.data.tuple.identifier.PairIdentifier;
import xyz.oliwer.genericframework.event.EventRegistry;

/**
 * This class represents the "Pair" (two values) implementation of {@link Tuple}.
 */
public final class Pair implements Tuple<PairIdentifier> {
    /** {@link Class<Tuple.Mutable>} the generic 'mutable' class accessor for this tuple. **/
    public static final Class<Tuple.Mutable<PairIdentifier>> MUTABLE_ACCESSOR =
        Tuple.Mutable.accessor();

    /** {@link Class<Tuple.Mutable>} the generic 'modified context' class accessor for this tuple. **/
    public static final Class<TupleModifiedContext<PairIdentifier, Mutable>> MODIFIED_ACCESSOR =
        TupleModifiedContext.accessor();

    /**
     * This class represents the mutable version of {@link Pair}.
     */
    public static final class Mutable implements Tuple.Mutable<PairIdentifier> {
        /** {@link Object} the first object in this mutable pair. **/
        private Object first;

        /** {@link Object} the second object in this mutable pair. **/
        private Object second;

        /** Primary constructor. **/
        public Mutable(Object first, Object second) {
            this.first = first;
            this.second = second;
        }

        /**
         * Fetch value by identifier.
         */
        @Override
        public synchronized <Type> Type get(PairIdentifier identifier) {
            // check null
            Tuple.checkNotNull(identifier);

            // handle accordingly
            switch (identifier) {
                case FIRST: return (Type) this.first;
                case SECOND: return (Type) this.second;
                default: return null;
            }
        }

        /**
         * Set value by identifier and passed object.
         */
        @Override
        public synchronized <Type> void set(PairIdentifier identifier, Type value) {
            // check null
            Tuple.checkNotNull(identifier);

            // old value
            final Object old;

            // assign accordingly
            switch (identifier) {
                case FIRST: {
                    old = this.first;
                    this.first = value;
                    break;
                }
                case SECOND: {
                    old = this.second;
                    this.second = value;
                    break;
                }
                default: throw new IllegalStateException("Invalid identifier");
            }

            // call event
            final EventRegistry registry = EventRegistry.get();
            registry.call(MODIFIED_ACCESSOR, new TupleModifiedContext<>(this, identifier, old, value));
        }
    }

    /** {@link Object} the first value assigned to this pair. **/
    private final Object first;

    /** {@link Object} the second value assigned to this pair. **/
    private final Object second;

    /** Primary constructor. **/
    public Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return {@link Type} the value of {@link PairIdentifier}.
     * @throws IllegalStateException if the provided identifier is null.
     */
    @Override
    public <Type> Type get(PairIdentifier type) {
        // check null
        Tuple.checkNotNull(type);

        // handle accordingly
        switch (type) {
            case FIRST: return (Type) this.first;
            case SECOND: return (Type) this.second;
            default: return null;
        }
    }
}