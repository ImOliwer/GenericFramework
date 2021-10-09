package xyz.oliwer.genericframework.data.utilization.file;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static java.lang.String.format;
import static java.util.Locale.ROOT;

/**
 * This functional interface represents an extension.
 * <p></p>
 * <b>Example usage;</b>
 * <pre>
 * if (isNotExtension(Path, Extensional))
 *   throw new RuntimeException("File must be '*'");
 * </pre>
 * Usage in action;
 * @see xyz.oliwer.genericframework.data.storage.JsonFileStorage
 */
public interface FileExtension {
    /**
     * This enumeration represents the extensions possible to validate.
     */
    enum Default implements FileExtension {
        JSON,
        TXT;

        public boolean by(Path path) {
            return isExtension(path, this);
        }

        @Override
        public @NotNull String getName() {
            return name().toLowerCase(ROOT);
        }
    }

    /**
     * Get whether a path is of a specific extension.
     *
     * @param path {@link Path} the path to validate.
     * @param extension {@link FileExtension} the extension required.
     * @return {@link Boolean}
     */
    static boolean isExtension(Path path, FileExtension extension) {
        final Path pathToName = path.getFileName();
        final String pathName = pathToName.toString();
        return pathName.endsWith(format(".%s", extension.getName()));
    }

    /**
     * Inversion of;
     * @see FileExtension#isExtension(Path, FileExtension)
     */
    static boolean isNotExtension(Path path, FileExtension extension) {
        return !isExtension(path, extension);
    }

    /**
     * Get the name of this extension.
     *
     * @return {@link String}
     */
    @NotNull String getName();
}