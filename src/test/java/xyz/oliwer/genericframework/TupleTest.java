package xyz.oliwer.genericframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.data.tuple.Tuple;
import xyz.oliwer.genericframework.data.tuple.identifier.PairIdentifier;
import xyz.oliwer.genericframework.data.tuple.identifier.QuadIdentifier;
import xyz.oliwer.genericframework.data.tuple.identifier.TripleIdentifier;
import xyz.oliwer.genericframework.data.tuple.identifier.TupleIdentifier;
import xyz.oliwer.genericframework.data.tuple.impl.Pair;
import xyz.oliwer.genericframework.data.tuple.impl.Quad;
import xyz.oliwer.genericframework.data.tuple.impl.Triple;
import xyz.oliwer.genericframework.event.EventRegistry;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public final class TupleTest {
    private static final Object EXPECTED_FIRST        = "string";
    private static final long EXPECTED_SECOND         = 1_900_000L;
    private static final long EXPECTED_SECOND_MUTABLE = 2_100_000L;
    private static final long EXPECTED_THIRD          = 3_000_000L;
    private static final long EXPECTED_FOURTH         = 4_000_000L;

    @Test void immutable() {
        final Pair pair = new Pair(EXPECTED_FIRST, EXPECTED_SECOND);
        final Triple triple = new Triple(EXPECTED_FIRST, EXPECTED_SECOND, EXPECTED_THIRD);
        final Quad quad = new Quad(EXPECTED_FIRST, EXPECTED_SECOND, EXPECTED_THIRD, EXPECTED_FOURTH);

        test(PairIdentifier.class, pair, EXPECTED_FIRST, EXPECTED_SECOND);
        test(TripleIdentifier.class, triple, EXPECTED_FIRST, EXPECTED_SECOND, EXPECTED_THIRD);
        test(QuadIdentifier.class, quad, EXPECTED_FIRST, EXPECTED_SECOND, EXPECTED_THIRD, EXPECTED_FOURTH);
    }

    @Test void pair() {
        final Pair.Mutable mutablePair = new Pair.Mutable(EXPECTED_FIRST, EXPECTED_SECOND);
        final EventRegistry eventRegistry = EventRegistry.get();

        eventRegistry.listen(Pair.MUTABLE_ACCESSOR, Pair.MODIFIED_ACCESSOR, context -> {
            if (context.getSource() != mutablePair) {
                return;
            }
            System.out.println(context.getIdentifier());
            assertEquals(EXPECTED_SECOND, (long) context.getPrevious());
            assertEquals(EXPECTED_SECOND_MUTABLE, (long) context.getUpdated());
        });

        mutablePair.set(PairIdentifier.SECOND, EXPECTED_SECOND + 200_000L);
    }

    private static <I extends TupleIdentifier, T extends Tuple<I>> void test(
        Class<I> enumClass,
        T instance,
        Object... arguments
    ) {
        final I[] identifiers = enumClass.getEnumConstants();
        for (int index = 0; index < identifiers.length; index++)
            assertEquals(arguments[index], instance.get(identifiers[index]));
    }
}