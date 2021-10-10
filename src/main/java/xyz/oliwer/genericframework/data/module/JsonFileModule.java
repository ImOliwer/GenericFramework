package xyz.oliwer.genericframework.data.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.oliwer.genericframework.data.utilization.Loadable;
import xyz.oliwer.genericframework.data.utilization.Savable;
import xyz.oliwer.genericframework.data.utilization.file.PathFetchVisitor;
import xyz.oliwer.genericframework.util.PrettyLogger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.nio.file.Files.walkFileTree;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static xyz.oliwer.genericframework.data.utilization.file.FileExtension.Default;
import static xyz.oliwer.genericframework.data.utilization.file.FileExtension.isNotExtension;
import static xyz.oliwer.genericframework.data.utilization.file.PathFetchVisitor.files;
import static xyz.oliwer.genericframework.util.Consumers.consumeAndTransform;

/**
 * This class represents the "json" implementation of {@link AbstractDataModule}.
 *
 * @param <Source> the type of source to load & save.
 */
public final class JsonFileModule<Source> extends AbstractDataModule<Source, Function<Path, Path>> {
    /** {@link PrettyLogger} this logger is used widely throughout the functionality in this class, and only in this class. **/
    private static final PrettyLogger LOGGER = new PrettyLogger("JsonFileLoader", null);

    /** {@link Path} origin path to load & save from. **/
    private final Path origin;

    /** {@link Gson} one and only instance to serialize & deserialize with. **/
    private final Gson gson;

    /** {@link Class<Source>} the class to be used during serialization and deserialization. **/
    private final Class<Source> sourceClass;

    /** {@link Boolean} whether to log debug messages. **/
    private final boolean debug;

    /**
     * Primary constructor.
     */
    @SafeVarargs
    public JsonFileModule(Class<Source> sourceClass, Path origin, boolean debug, Consumer<GsonBuilder>... gsonModifications) {
        super(newSingleThreadExecutor(it -> new Thread(it, "JsonFileLoader")));
        if (!Files.isDirectory(origin))
            throw new RuntimeException("Origin must be directory");
        this.origin = origin;
        this.gson = consumeAndTransform(new GsonBuilder(), gsonModifications, GsonBuilder::create);
        this.sourceClass = sourceClass;
        this.debug = debug;
    }

    /**
     * Load source by path.
     * @see Loadable#load(Object)
     */
    @Override
    public Source load(Function<Path, Path> pathFunc) {
        final Path exactPath = pathFunc.apply(this.origin);
        if (isNotExtension(exactPath, Default.JSON)) {
            throw new RuntimeException("Path provided is not forwarded to a JSON (\".json\") file.");
        }

        Source source;
        try (final Reader reader = new FileReader(exactPath.toFile())) {
            source = gson.fromJson(reader, sourceClass);
        } catch (Exception ignored) {
            if (debug)
                LOGGER.error("Failed to load JSON file at path '%s'", exactPath.toString());
            return null;
        }
        return source;
    }

    /**
     * @see Loadable#load(Object)
     * @return {@link Source}
     */
    private Source loadDirectly(Path single) {
        return load($ -> single);
    }

    /**
     * Attempt to load all 'json' files in the origin directory.
     * @see Loadable#loadAll()
     */
    @Override
    public List<Source> loadAll() {
        final List<Source> loaded = new LinkedList<>();
        final PathFetchVisitor visitor = (PathFetchVisitor) files(Default.JSON::by);

        try {
            walkFileTree(this.origin, visitor);
        } catch (IOException ignored) {
            if (debug)
                LOGGER.error("Failed to walk tree of origin (\"%s\")", origin.toString());
            return loaded;
        }

        final Queue<Path> visitations = (Queue<Path>) visitor.getSuccess();
        Path nextVisitation;

        while ((nextVisitation = visitations.poll()) != null)
            loaded.add(loadDirectly(nextVisitation));
        return loaded;
    }

    /**
     * Attempt to save a source by path.
     * @see Savable#save(Object, Object)
     */
    @Override
    public void save(Source source, Function<Path, Path> pathFunc) {
        final Path exactPath = pathFunc.apply(this.origin);
        if (isNotExtension(exactPath, Default.JSON)) {
            throw new RuntimeException("Path provided is not forwarded to a JSON (\".json\") file.");
        }

        try {
            final String serialized = gson.toJson(source, sourceClass);
            Files.write(exactPath, serialized.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception ignored) {
            if (debug)
                LOGGER.error("Failed to save JSON file to path '%s'", exactPath.toString());
        }
    }
}