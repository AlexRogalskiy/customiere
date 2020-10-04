package com.sensiblemetrics.api.customiere.crm.clients.model.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.siemens.microservices.sonarine.sensors.model.dto.iface.BaseViewEntityIF;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * {@link BaseViewEntityIF} model implementation
 *
 * @param <ID> type of document identifier
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = BaseViewEntityIF.VIEW_ID, namespace = "sonarine")
@JacksonXmlRootElement(localName = BaseViewEntityIF.VIEW_ID, namespace = "sonarine")
@ApiModel(value = BaseViewEntityIF.VIEW_ID, description = "Provide information on base view attributes")
public abstract class AbstractBaseViewEntity<ID extends Serializable> extends AbstractAuditViewEntity implements BaseViewEntityIF {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 602615748387358410L;

    @ApiModelProperty(value = "Model ID", name = ID_FIELD_NAME, example = "id", required = true)
    @JacksonXmlProperty(localName = ID_FIELD_NAME)
    @JsonProperty(ID_FIELD_NAME)
    private ID id;
}
