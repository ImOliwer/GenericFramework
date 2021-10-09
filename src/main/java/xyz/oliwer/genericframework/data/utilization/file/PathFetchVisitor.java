package xyz.oliwer.genericframework.data.utilization.file;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * This class represents the file visitor for fetching paths by success & failures - considering skips.
 */
public final class PathFetchVisitor implements FileVisitor<Path> {
    /** {@link List<Path>} all successful visitations. **/
    private final List<Path> success = new LinkedList<>();

    /** {@link List<Path>} all unsuccessful visitations. **/
    private final List<Path> failures = new LinkedList<>();

    /** {@link Boolean} whether to skip directories. **/
    private final boolean skipDirectories;

    /** {@link Boolean} whether to skip regular files. **/
    private final boolean skipFiles;

    /** {@link Predicate<Path>} filter to apply on file visitations. **/
    private final @Nullable Predicate<Path> fileFilter;

    /**
     * Primary constructor.
     *
     * @param skipDirectories {@link Boolean} whether to skip directories.
     * @param skipFiles {@link Boolean} whether to skip regular files.
     * @param fileFilter {@link Predicate<Path>} the filter to be applied on file visitations.
     */
    private PathFetchVisitor(boolean skipDirectories, boolean skipFiles, @Nullable Predicate<Path> fileFilter) {
        this.skipDirectories = skipDirectories;
        this.skipFiles = skipFiles;
        this.fileFilter = fileFilter;
    }

    /**
     * Visit file.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (!this.skipFiles && (fileFilter == null || fileFilter.test(file)))
            success.add(file);
        return CONTINUE;
    }

    /**
     * Failure attempting to visit file.
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        if (!this.skipFiles && exc != null) {
            success.remove(file);
            if (fileFilter == null || fileFilter.test(file))
                failures.add(file);
        }
        return CONTINUE;
    }

    /**
     * Pre-visiting directory.
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (!this.skipDirectories)
            success.add(dir);
        return CONTINUE;
    }

    /**
     * Post-visiting directory - a possible unsuccessful operation.
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        if (!this.skipDirectories && exc != null) {
            success.remove(dir);
            failures.add(dir);
        }
        return CONTINUE;
    }

    /**
     * @return {@link List<Path>} all successful visitations.
     */
    public @NotNull List<Path> getSuccess() {
        return this.success;
    }

    /**
     * @return {@link List<Path>} all unsuccessful visitations.
     */
    public @NotNull List<Path> getFailures() {
        return this.failures;
    }

    /**
     * Create a new instance of this visitor with only regular files in mind (skipping directories).
     *
     * @param filter {@link Predicate<Path>} the filter to apply on visitations.
     * @return {@link FileVisitor<Path>}
     */
    public static FileVisitor<Path> files(@Nullable Predicate<Path> filter) {
        return new PathFetchVisitor(true, false, filter);
    }

    /**
     * Create a new instance of this visitor with only directories in mind (skipping regular files).
     *
     * @return {@link FileVisitor<Path>}
     */
    public static FileVisitor<Path> directories() {
        return new PathFetchVisitor(false, true, null);
    }

    /**
     * Create a new instance of taking both regular files <b>AND</b> directories into account.
     *
     * @param fileFilter {@link Predicate<Path>} the filter to enforce on file visitations.
     * @return {@link FileVisitor<Path>}
     */
    public static FileVisitor<Path> both(@Nullable Predicate<Path> fileFilter) {
        return new PathFetchVisitor(false, false, fileFilter);
    }
}