package xyz.oliwer.genericframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.builder.WrappableAbstractBuilder;
import xyz.oliwer.genericframework.time.TimeFormatter;
import xyz.oliwer.genericframework.util.Wrappable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@TestInstance(Lifecycle.PER_CLASS)
public final class BuilderTest {
    // this occasion is a no-go-
    // at this point you could just use itself as a builder...
    // it was just made up for this example
    static final class FileWrapper implements Wrappable<File> {
        private String path;

        FileWrapper() {}

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public File unwrap() {
            return new File(path);
        }
    }

    static final class WrappableFileBuilder extends WrappableAbstractBuilder<File, FileWrapper> {
        public WrappableFileBuilder withPath(String path) {
            return (WrappableFileBuilder) push(wrapper -> wrapper.setPath(path));
        }

        public WrappableFileBuilder withPath(URL resource) {
            return withPath(resource.getPath());
        }

        @Override
        public WrappableFileBuilder withWrapper(FileWrapper wrapper) {
            return (WrappableFileBuilder) super.withWrapper(wrapper);
        }

        public Path buildAsPath() {
            return this.build().toPath();
        }
    }

    @Test void wrappable() throws IOException {
        System.out.println(TimeFormatter.format(new TimeFormatter.Options(null, null, null), 284012568000L));
        final Path path = new WrappableFileBuilder()
            .withPath(getClass().getClassLoader().getResource("example/document.txt"))
            .withWrapper(new FileWrapper())
            .buildAsPath();
        System.out.println(Files.readAllLines(path));
    }
}