package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeneralParamRequest<T> extends GeneralRequest<GeneralParamRequest.SearchParam<T>> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SearchParam<T> implements GeneralParam<String, T> {

        /**
         * Default search parameter key
         */
        @JsonProperty(value = "key", required = true)
        @ApiParam(value = "Search parameter key", name = "key", readOnly = true, required = true)
        @NonNull
        private String key;

        /**
         * Default search parameter value
         */
        @JsonProperty(value = "value", required = true)
        @ApiParam(value = "Search parameter value", name = "value", readOnly = true, required = true)
        @NonNull
        private T value;

        /**
         * Creates new {@link GeneralParam} for the given input elements
         *
         * @param key   - initial input param key {@link String}
         * @param value - initial input param value {@code T}
         * @return {@link GeneralParam}
         */
        public static <T> SearchParam<T> of(final String key, final T value) {
            return new SearchParam<>(key, value);
        }

        /**
         * Returns {@link SearchParam} if both {@link Optional} instances have values or {@link Optional#empty()} if one or both
         * are missing.
         *
         * @param key   - initial input param key {@link Optional}
         * @param value - initial input param value {@link Optional}
         * @return {@link Optional} of {@link SearchParam}
         */
        public static <T> Optional<SearchParam<T>> with(final Optional<String> key, final Optional<T> value) {
            return key.flatMap(k -> value.map(v -> SearchParam.of(k, v)));
        }
    }
}
