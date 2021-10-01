package xyz.oliwer.genericframework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.service.Service;
import xyz.oliwer.genericframework.service.global.WatchableService;
import xyz.oliwer.genericframework.watcher.WatchableString;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public final class WatchableTest {
    private WatchableString watchableString;

    @BeforeAll void watchables() {
        final WatchableService service = Service.watchable();
        this.watchableString = service.watch(String.class, "Hello!");
    }

    @Test void string() {
        if (watchableString == null) {
            throw new IllegalStateException("Watchable String is null.");
        }

        watchableString.attach((oldValue, newValue) -> System.out.printf(
            "old value = %s; new value = %s; can parse to int = %s\n",
            oldValue, newValue, watchableString.canParseTo(int.class)
        ));

        watchableString.update("3");
        assertEquals(3, watchableString.parseTo(int.class));
    }
}