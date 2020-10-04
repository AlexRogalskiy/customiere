package com.sensiblemetrics.api.customiere.crm.clients.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.iface.SensorRecordViewEntityIF;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {@link SensorRecordViewEntityIF} model implementation
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(
    value = {
        AbstractBaseViewEntity.ID_FIELD_NAME,
        SensorRecordViewEntityIF.SENSOR_ID_FIELD_NAME,
        SensorRecordViewEntityIF.LOCATION_FIELD_NAME,
        SensorRecordViewEntityIF.DATA_FIELD_NAME
    },
    alphabetic = true
)
@JsonRootName(value = SensorRecordViewEntityIF.VIEW_ID, namespace = "sonarine")
@JacksonXmlRootElement(localName = SensorRecordViewEntityIF.VIEW_ID, namespace = "sonarine")
@ApiModel(value = SensorRecordViewEntityIF.VIEW_ID, description = "Provide information on sensor metrics view attributes")
public class SensorRecordViewEntity extends AbstractBaseModelViewEntity<Long> implements SensorRecordViewEntityIF {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -6478428404940010949L;

    @ApiModelProperty(value = "Metrics ID", name = ID_FIELD_NAME, example = "id", required = true)
    @JacksonXmlProperty(localName = ID_FIELD_NAME)
    @JsonProperty(ID_FIELD_NAME)
    private Long id;

//    @ApiModelProperty(value = "Sensor identifier", name = SENSOR_ID_FIELD_NAME, example = "sensorId", required = true)
//    @JacksonXmlProperty(localName = SENSOR_ID_FIELD_NAME)
//    @JsonProperty(SENSOR_ID_FIELD_NAME)
//    private UUID sensorId;

    @ApiModelProperty(value = "Metrics geolocation", name = LOCATION_FIELD_NAME, example = "location", required = true)
    @JacksonXmlProperty(localName = LOCATION_FIELD_NAME)
    @JsonProperty(LOCATION_FIELD_NAME)
    private GeolocationEntity location;

    @ApiModelProperty(value = "Metrics data", name = DATA_FIELD_NAME, example = "data", required = true)
    @JacksonXmlProperty(localName = DATA_FIELD_NAME)
    @JsonProperty(DATA_FIELD_NAME)
    private String data;

    @ApiModelProperty(value = "Metrics of sensor", name = SENSOR_FIELD_NAME, example = "sensor", required = true)
    @JacksonXmlProperty(localName = SENSOR_FIELD_NAME)
    @JsonProperty(SENSOR_FIELD_NAME)
    private SensorViewEntity sensor;
}
