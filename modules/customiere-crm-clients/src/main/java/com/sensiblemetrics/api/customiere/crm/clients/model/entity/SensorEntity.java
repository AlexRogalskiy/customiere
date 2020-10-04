package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.siemens.microservices.sonarine.sensors.enumeration.SensorStatusType;
import com.siemens.microservices.sonarine.sensors.model.dao.iface.SensorEntityIF;
import com.siemens.microservices.sonarine.sensors.model.dao.type.GeolocationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = SensorEntityIF.MODEL_ID)
@Table(
    name = SensorEntityIF.TABLE_NAME,
    indexes = {@Index(name = "name_idx", columnList = "name")},
    uniqueConstraints = {@UniqueConstraint(name = "name_location_unq", columnNames = {"name", "location"})}
)
@TypeDef(name = "SensorGeolocationEntity", defaultForType = SensorEntity.class, typeClass = GeolocationType.class)
@AttributeOverrides({
    @AttributeOverride(name = AbstractBaseEntity.ID_FIELD_NAME, column = @Column(name = SensorEntityIF.ID_FIELD_NAME, unique = true, nullable = false))
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@BatchSize(size = 10)
@Indexed(index = SensorEntityIF.MODEL_ID)
public class SensorEntity extends AbstractBaseEntity<UUID> implements SensorEntityIF {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1591260887009630056L;

    @NotBlank(message = "sensor.name.notBlank")
    @Pattern(regexp = "^[\\w-]*$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.UNICODE_CASE}, message = "sensor.name.pattern")
    @Size(max = 100, message = "sensor.name.size")
    @Column(name = NAME_FIELD_NAME, nullable = false)
    private String name;

    @Pattern(regexp = "^[\\w-]*$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.UNICODE_CASE}, message = "sensor.description.pattern")
    @Size(max = 250, message = "sensor.description.size")
    @Column(name = DESCRIPTION_FIELD_NAME, nullable = false)
    private String description;

    @NotNull(message = "sensor.status.notNull")
    @Enumerated(EnumType.STRING)
    @Column(name = SENSOR_STATUS_FIELD_NAME, nullable = false, length = 20)
    private SensorStatusType status;

    @NotNull(message = "sensor.location.notNull")
    @Type(type = "com.siemens.microservices.sonarine.sensors.model.dao.type.GeolocationType")
    @Columns(columns = {
        @Column(name = LATITUDE_FIELD_NAME, precision = 10, scale = 2, nullable = false),
        @Column(name = LONGITUDE_FIELD_NAME, precision = 10, scale = 2, nullable = false)}
    )
    private GeolocationEntity location;

    @ContainedIn
    @OneToMany(mappedBy = SENSOR_FIELD_NAME, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Column(name = RECORDS_FIELD_NAME, nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotFound(action = NotFoundAction.IGNORE)
    @BatchSize(size = 10)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final Set<SensorRecordEntity> sensorRecords = new HashSet<>();

    public void setSensorRecords(final Iterable<? extends SensorRecordEntity> sensorRecords) {
        this.getSensorRecords().clear();
        Optional.ofNullable(sensorRecords)
            .orElseGet(Collections::emptyList)
            .forEach(this::addSensorRecord);
    }

    public void addSensorRecord(final SensorRecordEntity sensorRecord) {
        if (Objects.nonNull(sensorRecord)) {
            this.getSensorRecords().add(sensorRecord);
        }
    }
}
