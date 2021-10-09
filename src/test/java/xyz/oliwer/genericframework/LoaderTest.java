package xyz.oliwer.genericframework;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.data.storage.JsonFileStorage;
import xyz.oliwer.genericframework.data.utilization.Loadable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import static java.lang.String.format;

@TestInstance(Lifecycle.PER_CLASS)
public final class LoaderTest {
    public static final class Property {
        final String name;

        public Property(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return format("Property{name='%s'}", this.name);
        }
    }

    @Test void json_file() {
        final Loadable<Property, Function<Path, Path>> propertyLoader = new JsonFileStorage<>(
            Property.class,
            Paths.get("G:\\Programming\\General\\GenericFramework\\src\\test\\resources\\example"),
            true,
            GsonBuilder::setPrettyPrinting
        );

        propertyLoader.loadAsync(origin -> origin.resolve("property.json"))
            .whenComplete((property, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                    return;
                }
                System.out.printf("\bloaded property = %s\n", property);
            });
    }
}