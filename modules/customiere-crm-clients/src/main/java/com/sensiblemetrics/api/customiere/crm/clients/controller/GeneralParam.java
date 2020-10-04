package com.sensiblemetrics.api.customiere.crm.clients.controller;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * General param declaration
 *
 * @param <K> type of param key {@code K}
 * @param <V> type of param value {@code V}
 * @author Alex
 * @version 1.0.0
 */
public interface GeneralParam<K, V> {

    /**
     * Returns param key {@code K}
     *
     * @return param key {@code K}
     */
    @Nullable
    K getKey();

    /**
     * Returns param value {@code V}
     *
     * @return param value {@code V}
     */
    @Nullable
    V getValue();

    /**
     * A collector to create a {@link Map} from a {@link Stream} of {@link GeneralParam}s.
     *
     * @param <K> type of node key
     * @param <V> type of node value
     * @return {@link Map} of {@link GeneralParam}
     */
    static <K, V> Collector<GeneralParam<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(GeneralParam::getKey, GeneralParam::getValue);
    }

    /**
     * Invokes the given {@link BiConsumer} if all given {@link Optional} are present
     *
     * @param <K>      type of node key
     * @param <V>      type of node value
     * @param key      - initial input key {@link Optional}
     * @param value    - initial input value {@link Optional}
     * @param consumer - initial input {@link BiConsumer} operator
     */
    static <K, V> void ifAllPresent(final Optional<K> key, final Optional<V> value, final BiConsumer<K, V> consumer) {
        Objects.requireNonNull(key, "Optional must not be null!");
        Objects.requireNonNull(value, "Optional must not be null!");
        Objects.requireNonNull(consumer, "Consumer must not be null!");

        mapIfAllPresent(key, value, (k, v) -> {
            consumer.accept(k, v);
            return null;
        });
    }

    /**
     * Maps the values contained in the given {@link Optional} if both of them are present
     *
     * @param <K>      type of node key
     * @param <V>      type of node value
     * @param <R>      type of function result
     * @param key      - initial input key {@link Optional}
     * @param value    - initial input value {@link Optional}
     * @param function - initial input {@link BiFunction} operator
     * @return {@link Optional} of {@link BiFunction} operator result {@code R}
     */
    static <K, V, R> Optional<R> mapIfAllPresent(final Optional<K> key, final Optional<V> value, final BiFunction<K, V, R> function) {
        Objects.requireNonNull(key, "Optional must not be null!");
        Objects.requireNonNull(value, "Optional must not be null!");
        Objects.requireNonNull(function, "BiFunctionmust not be null!");

        return key.flatMap(k -> value.map(v -> function.apply(k, v)));
    }
}
