package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.util.Collection;

@Data
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "request")
@JsonSubTypes({
    @JsonSubTypes.Type(value = GeneralParamRequest.class, name = "param-request"),
    @JsonSubTypes.Type(value = SensorDataGetRequest.class, name = "param-request")
})
public class GeneralRequest<T> {

    @JsonProperty("items")
    @ApiParam(value = "Items to fetch result by", name = "items", example = "[\"9e1b6181-0f11-4c15-911d-ee2739b454cc\", \"21814afc-c1bc-41e6-b6e6-710bf7fd34ce\"]")
    private Collection<T> items;

    @JsonProperty("page")
    @ApiParam(value = "Page request to fetch result by", name = "page", example = "{\"offset:\" 0, \"limit\": 5, \"sort\": {\"name\": \"name\", \"order\": \"ASC\"}}")
    private GeneralRequest.PageRequest page;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class PageRequest {

        @JsonProperty("offset")
        @ApiParam(value = "Page offset number to return records from (starts from 0)", name = "offset", defaultValue = "0")
        private int offset;

        @JsonProperty("limit")
        @ApiParam(value = "Page limit number of records to return by single page", name = "limit", defaultValue = "0")
        private int limit;

        @JsonProperty("sort")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiParam(value = "Page records sort order", name = "sort", allowableValues = "{ASC,DESC}", example = "{\"name\": \"name\", \"order\": \"ASC\"}")
        private Collection<GeneralRequest.FieldSort> sort;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FieldSort {

        @JsonProperty(value = "name", required = true)
        @ApiParam(value = "Field name", name = "name", example = "name", readOnly = true, required = true)
        private String name;

        @JsonProperty(value = "order", required = true)
        @ApiParam(value = "Field sort direction", name = "order", allowableValues = "ASC, DESC", readOnly = true, required = true)
        private Sort.Direction order;
    }
}
