package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siemens.microservices.sonarine.sensors.model.dao.iface.BaseEntityIF;
import com.siemens.microservices.sonarine.sensors.model.dao.listener.EventListener;
import com.siemens.microservices.sonarine.sensors.model.iface.Versionable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Objects;

/**
 * {@link BaseEntityIF} model implementation
 *
 * @param <ID> type of model identifier {@link Serializable}
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
@EntityListeners(EventListener.class)
public abstract class AbstractBaseEntity<ID extends Serializable> extends AbstractAuditEntity<ID> implements BaseEntityIF<ID> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6444143028591284804L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "base_model_id_gen")
    @SequenceGenerator(name = "base_model_id_gen", sequenceName = "base_model_id_seq", allocationSize = 1)
    @Column(name = ID_FIELD_NAME, unique = true, nullable = false)
    private ID id;

    @Version
    @ColumnDefault("0")
    @PositiveOrZero(message = "base.version.positiveOrZero")
    @Column(name = VERSION_FIELD_NAME, insertable = false, updatable = false)
    //@Generated(GenerationTime.ALWAYS)
    private Long version;

    @ApiModelProperty(hidden = true)
    @Override
    @JsonIgnore
    public boolean isNew() {
        return Objects.isNull(this.getId());
    }
}
