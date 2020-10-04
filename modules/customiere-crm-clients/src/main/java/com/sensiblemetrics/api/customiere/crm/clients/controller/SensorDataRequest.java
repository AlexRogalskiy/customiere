package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.siemens.microservices.sonarine.sensors.enumeration.MetricsType;
import com.siemens.microservices.sonarine.sensors.enumeration.SensorStatusType;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SensorDataRequest {

    @JsonProperty(value = "sensorId", required = true)
    @NotNull(message = "SensorDataRequest <sensorId> property should not be null")
    @ApiParam(value = "Sensor identifier", name = "sensorId", example = "f46c35a0-1042-4859-a89a-730f13ecb65c", readOnly = true, required = true)
    private UUID sensorId;

    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "SensorDataRequest <name> property should not be blank")
    @ApiParam(value = "Sensor name", name = "name", example = "name", readOnly = true, required = true)
    private String name;

    @JsonProperty("description")
    @ApiParam(value = "Sensor description", name = "description", example = "description", readOnly = true)
    private String description;

    @NestedConfigurationProperty
    @JsonProperty(value = "status", required = true)
    @NotNull(message = "SensorDataRequest <status> property should not be null")
    @ApiParam(value = "Sensor status", name = "status", example = "ACTIVE", allowableValues = "{ACTIVE, INACTIVE, PENDING, BLOCKED}", readOnly = true, required = true)
    private SensorStatusType status;

    @NestedConfigurationProperty
    @JsonProperty(value = "coordinates", required = true)
    @NotNull(message = "SensorDataRequest <coordinates> property should not be null")
    @ApiParam(value = "Geolocation coordinates", name = "coordinates", example = "{\"125.256\", \"568.457\"}", readOnly = true, required = true)
    private Coordinates coordinates;

    @JsonProperty("metrics")
    @NotNull(message = "SensorDataRequest <metrics> property should not be null")
    @ApiParam(value = "Sensor metrics", name = "metrics", example = "{\"type\": \"TEMPERATURE\", data: \"159\", \"timestamp\":\"9999-12-31 23:59:59\"}")
    private Collection<Metrics> metrics;

    @Data
    @Validated
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Coordinates {

        @JsonProperty(value = "longitude", required = true)
        @NotBlank(message = "Coordinates <longitude> property should not be blank")
        @ApiParam(value = "Geolocation longitude coordinate", name = "longitude", example = "125.256", readOnly = true, required = true)
        private String longitude;

        @JsonProperty(value = "latitude", required = true)
        @NotBlank(message = "Coordinates <latitude> property should not be blank")
        @ApiParam(value = "Geolocation latitude coordinate", name = "latitude", example = "568.457", readOnly = true, required = true)
        private String latitude;
    }

    @Data
    @Validated
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Metrics {

        @JsonEnumDefaultValue
        @JsonProperty(value = "type", required = true)
        @NotNull(message = "Metrics <type> property should not be null")
        @ApiParam(value = "Metrics type", name = "type", example = "TEMPERATURE", allowableValues = "{TEMPERATURE, HUMIDITY, PRESSURE, UNDEFINED}", readOnly = true, required = true)
        private MetricsType type;

        @JsonProperty(value = "data", required = true)
        @NotBlank(message = "Metrics <data> property should not be blank")
        @ApiParam(value = "Metrics data", name = "data", example = "0", readOnly = true, required = true)
        private String data;

        @JsonProperty(value = "timestamp", required = true)
        @NotNull(message = "Metrics <timestamp> property should not be null")
        @ApiParam(value = "Metrics timestamp", name = "timestamp", example = "9999-12-31 23:59:59", readOnly = true, required = true)
        private LocalDateTime timestamp;
    }
}
