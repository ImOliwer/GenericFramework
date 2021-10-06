package xyz.oliwer.genericframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.data.tuple.Tuple;
import xyz.oliwer.genericframework.data.tuple.event.TupleModifiedContext;
import xyz.oliwer.genericframework.data.tuple.identifier.PairIdentifier;
import xyz.oliwer.genericframework.data.tuple.impl.Pair;
import xyz.oliwer.genericframework.event.EventRegistry;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public final class TupleTest {
    private static final Object EXPECTED_FIRST        = "string";
    private static final long EXPECTED_SECOND         = 1_900_000L;
    private static final long EXPECTED_SECOND_MUTABLE = 2_000_000L;

    @Test void immutable() {
        final Pair pair = new Pair(EXPECTED_FIRST, EXPECTED_SECOND);
        assertEquals(EXPECTED_FIRST, pair.get(PairIdentifier.FIRST));
        assertEquals((Object) EXPECTED_SECOND, pair.get(PairIdentifier.SECOND));
    }

    @Test void mutable() {
        final Pair.Mutable mutablePair = new Pair.Mutable(EXPECTED_FIRST, EXPECTED_SECOND);
        final EventRegistry eventRegistry = EventRegistry.get();

        eventRegistry.listen(Tuple.Mutable.class, TupleModifiedContext.class, context -> {
            if (context.getSource() != mutablePair) {
                return;
            }
            assertEquals(EXPECTED_SECOND, (long) context.getPrevious());
            assertEquals(EXPECTED_SECOND_MUTABLE, (long) context.getUpdated());
        });

        mutablePair.set(PairIdentifier.SECOND, EXPECTED_SECOND + 100_000L);
    }
}