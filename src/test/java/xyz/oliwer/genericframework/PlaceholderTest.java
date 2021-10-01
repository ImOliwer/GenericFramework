package xyz.oliwer.genericframework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor;
import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor.Placeholder;
import xyz.oliwer.genericframework.placeholder.PlaceholderProcessor.Data;
import xyz.oliwer.genericframework.service.Service;
import xyz.oliwer.genericframework.service.global.PlaceholderService;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.oliwer.genericframework.placeholder.PlaceholderProcessor.byReplace;

@TestInstance(Lifecycle.PER_CLASS)
public final class PlaceholderTest {
    private static class PropertyOwner {
        final String name = "Robert";
        final byte   age  = 31;
    }

    private PropertyOwner owner;

    private final Data<PropertyOwner> EXAMPLE = new Data<>(
        asList(
            new Placeholder<>("{name}", provider -> provider.name),
            new Placeholder<>("{age}", provider -> String.valueOf(provider.age))
        )
    );

    @BeforeAll void setOwner() {
        this.owner = new PropertyOwner();
    }

    @Test void results() {
        // attach to service & fetch (example - you should obviously not do this in the same context during an actual situation)
        final PlaceholderService service = Service.placeholder();
        service.attach(PropertyOwner.class, byReplace(EXAMPLE));
        final PlaceholderProcessor<PropertyOwner> processor = service.get(PropertyOwner.class);

        // processing
        final String origin = "{name}, {age}";
        final long start = System.currentTimeMillis();
        final String processed = processor.process(this.owner, origin);
        final long end = System.currentTimeMillis();
        System.out.printf("process took approximately %sms\n", end - start);

        // test
        assertEquals("Robert, 31", processed);
    }
}