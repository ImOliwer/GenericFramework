package xyz.oliwer.genericframework.data.tuple.impl;

import xyz.oliwer.genericframework.data.tuple.Tuple;
import xyz.oliwer.genericframework.data.tuple.event.TupleModifiedContext;
import xyz.oliwer.genericframework.data.tuple.identifier.TripleIdentifier;
import xyz.oliwer.genericframework.event.EventRegistry;

/**
 * This class represents the "Triple" (three values) implementation of {@link Tuple}.
 */
public final class Triple implements Tuple<TripleIdentifier> {
    /** {@link Class<Tuple.Mutable>} the generic 'mutable' class accessor for this tuple. **/
    public static final Class<Tuple.Mutable<TripleIdentifier>> MUTABLE_ACCESSOR =
        Tuple.Mutable.accessor();

    /** {@link Class<Tuple.Mutable>} the generic 'modified context' class accessor for this tuple. **/
    public static final Class<TupleModifiedContext<TripleIdentifier, Mutable>> MODIFIED_ACCESSOR =
        TupleModifiedContext.accessor();

    /**
     * This class represents the mutable version of {@link Triple}.
     */
    public static final class Mutable implements Tuple.Mutable<TripleIdentifier> {
        /** {@link Object} the first object in this mutable tuple. **/
        private Object first;

        /** {@link Object} the second object in this mutable tuple. **/
        private Object second;

        /** {@link Object} the third object in this mutable tuple. **/
        private Object third;

        /** Primary constructor. **/
        public Mutable(Object first, Object second, Object third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        /**
         * Fetch value by identifier.
         */
        @Override
        public synchronized <Type> Type get(TripleIdentifier identifier) {
            // check null
            Tuple.checkNotNull(identifier);

            // handle accordingly
            switch (identifier) {
                case FIRST: return (Type) this.first;
                case SECOND: return (Type) this.second;
                case THIRD: return (Type) this.third;
                default: return null;
            }
        }

        /**
         * Set value by identifier and passed object.
         */
        @Override
        public synchronized <Type> void set(TripleIdentifier identifier, Type value) {
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
                case THIRD: {
                    old = this.third;
                    this.third = value;
                    break;
                }
                default: throw new IllegalStateException("Invalid identifier");
            }

            // call event
            final EventRegistry registry = EventRegistry.get();
            registry.call(MODIFIED_ACCESSOR, new TupleModifiedContext<>(this, identifier, old, value));
        }
    }

    /** {@link Object} the first value assigned to this tuple. **/
    private final Object first;

    /** {@link Object} the second value assigned to this tuple. **/
    private final Object second;

    /** {@link Object} the third value assigned to this tuple. **/
    private final Object third;

    /** Primary constructor. **/
    public Triple(Object first, Object second, Object third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * @return {@link Type} the value of {@link TripleIdentifier}.
     * @throws IllegalStateException if the provided identifier is null.
     */
    @Override
    public <Type> Type get(TripleIdentifier type) {
        // check null
        Tuple.checkNotNull(type);

        // handle accordingly
        switch (type) {
            case FIRST: return (Type) this.first;
            case SECOND: return (Type) this.second;
            case THIRD: return (Type) this.third;
            default: return null;
        }
    }
}