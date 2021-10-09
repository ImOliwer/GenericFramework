package xyz.oliwer.genericframework.util;

import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * This class represents a more formed & expanded implementation of {@link Logger}.
 */
public final class PrettyLogger extends Logger {
    /**
     * @see Logger#Logger(String, String)
     */
    public PrettyLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    /**
     * Log an error with placeholders.
     *
     * @param placeholders {@link Object} array of placeholders to format via provided message.
     * @see Logger#severe(String)
     */
    public void error(String message, Object... placeholders) {
        severe(format(message, placeholders));
    }

    /**
     * Log a warning with placeholders.
     *
     * @param placeholders {@link Object} array of placeholders to format via provided message.
     * @see Logger#warning(String)
     */
    public void warn(String message, Object... placeholders) {
        warning(format(message, placeholders));
    }

    /**
     * Log information with placeholders.
     *
     * @param placeholders {@link Object} array of placeholders to format via provided message.
     * @see Logger#info(String)
     */
    public void info(String message, Object... placeholders) {
        info(format(message, placeholders));
    }
}