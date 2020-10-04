package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.siemens.microservices.sonarine.sensors.converter.BaseListToStringConverter;
import com.siemens.microservices.sonarine.sensors.model.constraint.annotation.UUID;
import com.siemens.microservices.sonarine.sensors.model.dao.iface.SensorRecordEntityIF;
import com.siemens.microservices.sonarine.sensors.model.dao.type.GeolocationType;
import com.siemens.microservices.sonarine.sensors.model.dto.domain.MetricsRecordViewEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * {@link SensorRecordEntityIF} model implementation
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = SensorRecordEntityIF.MODEL_ID)
@Table(name = SensorRecordEntityIF.TABLE_NAME)
@TypeDef(name = "SensorRecordGeolocationEntity", defaultForType = SensorRecordEntity.class, typeClass = GeolocationType.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@BatchSize(size = 10)
@Indexed(index = SensorRecordEntityIF.MODEL_ID)
public class SensorRecordEntity extends AbstractBaseModelEntity<Long> implements SensorRecordEntityIF {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -2544572211075106517L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "metrics_id_gen")
    @SequenceGenerator(name = "metrics_id_gen", sequenceName = "metrics_id_seq", allocationSize = 1)
    @Column(name = ID_FIELD_NAME, unique = true, nullable = false)
    private Long id;

    @UUID(message = "metrics.messageId.uuid")
    @NotBlank(message = "metrics.messageId.notBlank")
    @Column(name = SENSOR_ID_FIELD_NAME, nullable = false, length = 100)
    private String messageId;

    @NotNull(message = "metrics.location.notNull")
    @Type(type = "com.siemens.microservices.sonarine.sensors.model.dao.type.GeolocationType")
    @Columns(columns = {
        @Column(name = LATITUDE_FIELD_NAME, precision = 10, scale = 2, nullable = false)
        ,
        @Column(name = LONGITUDE_FIELD_NAME, precision = 10, scale = 2, nullable = false)}
    )
    private GeolocationEntity location;

    @ContainedIn
    @Column(name = METRICS_FIELD_NAME, nullable = false)
    @Convert(converter = BaseListToStringConverter.class)
    @BatchSize(size = 10)
    private final List<MetricsRecordViewEntity> metricsRecords = new ArrayList<>();

    @IndexedEmbedded
    @NotNull(message = "metrics.sensor.notNull")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = SENSOR_FIELD_NAME, nullable = false)
    private SensorEntity sensor;

    public void setMetrics(final Iterable<? extends MetricsRecordViewEntity> metricsRecords) {
        this.getMetricsRecords().clear();
        Optional.ofNullable(metricsRecords)
            .orElseGet(Collections::emptyList)
            .forEach(this::addMetrics);
    }

    public void addMetrics(final MetricsRecordViewEntity metricsRecord) {
        if (Objects.nonNull(metricsRecord)) {
            this.getMetricsRecords().add(metricsRecord);
        }
    }
}
