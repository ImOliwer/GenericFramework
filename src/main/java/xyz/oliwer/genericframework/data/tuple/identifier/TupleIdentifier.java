package xyz.oliwer.genericframework.data.tuple.identifier;

import xyz.oliwer.genericframework.data.tuple.Tuple;

/**
 * This interface does nothing other than hold together implementations
 * via their own identifiers & for said implementations to cover the
 * {@link Tuple#get(TupleIdentifier)} method appropriately.
 */
public interface TupleIdentifier {}