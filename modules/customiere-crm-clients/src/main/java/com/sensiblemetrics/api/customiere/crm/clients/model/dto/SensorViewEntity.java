package com.sensiblemetrics.api.customiere.crm.clients.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.siemens.microservices.sonarine.sensors.enumeration.SensorStatusType;
import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.iface.SensorViewEntityIF;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(
    value = {
        SensorViewEntityIF.ID_FIELD_NAME,
        SensorViewEntityIF.NAME_FIELD_NAME,
        SensorViewEntityIF.DESCRIPTION_FIELD_NAME,
        SensorViewEntityIF.STATUS_FIELD_NAME,
        SensorViewEntityIF.LOCATION_FIELD_NAME,
        SensorViewEntityIF.METRICS_FIELD_NAME
    },
    alphabetic = true
)
@JsonRootName(value = SensorViewEntityIF.VIEW_ID, namespace = "sonarine")
@JacksonXmlRootElement(localName = SensorViewEntityIF.VIEW_ID, namespace = "sonarine")
@ApiModel(value = SensorViewEntityIF.VIEW_ID, description = "Provide information on sensor view attributes")
public class SensorViewEntity extends AbstractBaseViewEntity<UUID> implements SensorViewEntityIF {

    @ApiModelProperty(value = "Sensor name", name = NAME_FIELD_NAME, example = "name", required = true)
    @JacksonXmlProperty(localName = NAME_FIELD_NAME)
    @JsonProperty(NAME_FIELD_NAME)
    private String name;

    @ApiModelProperty(value = "Sensor description", name = DESCRIPTION_FIELD_NAME, example = "description")
    @JacksonXmlProperty(localName = DESCRIPTION_FIELD_NAME)
    @JsonProperty(DESCRIPTION_FIELD_NAME)
    private String description;

    @ApiModelProperty(value = "Sensor status", name = STATUS_FIELD_NAME, example = "ACTIVE", required = true)
    @JacksonXmlProperty(localName = STATUS_FIELD_NAME)
    @JsonProperty(STATUS_FIELD_NAME)
    private SensorStatusType status;

    @ApiModelProperty(value = "Sensor geolocation", name = LOCATION_FIELD_NAME, example = "location", required = true)
    @JacksonXmlProperty(localName = LOCATION_FIELD_NAME)
    @JsonProperty(LOCATION_FIELD_NAME)
    private GeolocationEntity location;

    @ApiModelProperty(value = "List of metrics per sensor", name = METRICS_FIELD_NAME, example = "metrics")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = METRICS_FIELD_NAME)
    @JsonProperty(METRICS_FIELD_NAME)
    private final Set<SensorRecordViewEntity> metrics = new HashSet<>();
}
