package xyz.oliwer.genericframework.placeholder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This functional interface holds the single function to handle
 * the process of an origin {@link String} by passed provider & replacements through {@link Data}.
 *
 * @param <Provider> the type of provider involved in this processor.
 */
@FunctionalInterface
public interface PlaceholderProcessor<Provider> {
    /**
     * This data class holds a placeholder, and it's replacement.
     */
    final class Placeholder<Provider> {
        private final String placeholder;
        private final Function<Provider, Object> replacement;

        public Placeholder(String placeholder, Function<Provider, Object> replacement) {
            this.placeholder = placeholder;
            this.replacement = replacement;
        }
    }

    /**
     * This class holds the data for a placeholder processor.
     */
    final class Data<Provider> {
        private final List<Placeholder<Provider>> replacements;

        public Data(final List<Placeholder<Provider>> replacements) {
            this.replacements = new CopyOnWriteArrayList<>(replacements);
        }

        /**
         * Append a new placeholder to this processor data.
         *
         * @param placeholder {@link Placeholder<Provider>} the placeholder instance to append.
         * @return {@link Boolean} whether the 'append' operation was successful.
         */
        public synchronized boolean append(Placeholder<Provider> placeholder) {
            return replacements.add(placeholder);
        }

        /**
         * @see PlaceholderProcessor.Data#append(Placeholder)
         */
        public synchronized boolean append(String placeholder, Function<Provider, Object> replacement) {
            return append(new Placeholder<>(placeholder, replacement));
        }

        /**
         * Detach a placeholder from this processor data.
         *
         * @param placeholder {@link Placeholder<Provider>} the placeholder instance to detach.
         * @return {@link Boolean} whether the 'detach' operation was successful.
         */
        public synchronized boolean detach(Placeholder<Provider> placeholder) {
            return replacements.remove(placeholder);
        }

        /**
         * @see PlaceholderProcessor.Data#detach(Placeholder)
         */
        public synchronized boolean detach(String placeholder, boolean caseInsensitive) {
            return replacements.removeIf(it -> {
                final String found = it.placeholder;
                return !caseInsensitive ? found.equals(placeholder) : found.equalsIgnoreCase(placeholder);
            });
        }

        /**
         * Get a freshly created instance of this class along with applied
         * modifications via passed consumer.
         *
         * @param builder {@link Consumer<Data>} changes to apply to the new instance.
         * @return {@link Data<Provider>} fresh instance with said modifications.
         */
        public static <Provider> Data<Provider> from(Consumer<Data<Provider>> builder) {
            final Data<Provider> data = new Data<>(new ArrayList<>());
            builder.accept(data);
            return data;
        }
    }

    /**
     * Create a new processor with the "replace" functionality by provided data.
     *
     * @param data {@link Data<Provider>} the data containing all available replacements to be used.
     * @return {@link PlaceholderProcessor<Provider>} freshly created instance of a "replace" processor.
     */
    static <Provider> PlaceholderProcessor<Provider> byReplace(Data<Provider> data) {
        return (provider, origin) -> {
            String updated = origin;

            for (Placeholder<Provider> holder : data.replacements) updated = updated.replace(
                holder.placeholder, holder.replacement.apply(provider).toString()
            );

            return updated;
        };
    }

    /**
     * @see PlaceholderProcessor#byReplace(Data)
     */
    static <Provider> PlaceholderProcessor<Provider> byReplace(Consumer<Data<Provider>> builder) {
        return byReplace(Data.from(builder));
    }

    /**
     * Process the replacements of a {@link String} by origin & provider.
     *
     * @param provider {@link Provider} the provider involved in this process.
     * @param origin {@link String} the origin string value to replace placeholders in.
     * @return {@link String} completed process of origin.
     */
    String process(Provider provider, String origin);
}