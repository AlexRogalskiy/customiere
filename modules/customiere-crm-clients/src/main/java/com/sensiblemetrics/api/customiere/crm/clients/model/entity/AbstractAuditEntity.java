package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.siemens.microservices.sonarine.sensors.model.constraint.annotation.ChronologicalDates;
import com.siemens.microservices.sonarine.sensors.model.dao.iface.AuditEntityIF;
import com.siemens.microservices.sonarine.sensors.utility.DateUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link AuditEntityIF} model implementation
 *
 * @param <ID> type of model identifier {@link Serializable}
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@ChronologicalDates
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity<ID extends Serializable> implements AuditEntityIF<ID> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 431774856692738135L;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT)
    @NotNull(message = "audit.created.notNull")
    @Column(name = CREATED_FIELD_NAME, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Setter
    @CreatedBy
    @NotBlank(message = "audit.createdBy.notBlank")
    @Column(name = CREATED_BY_FIELD_NAME, nullable = false, updatable = false)
    private String createdBy;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT)
    @Column(name = CHANGED_FIELD_NAME, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Setter
    @LastModifiedBy
    @Column(name = CHANGED_BY_FIELD_NAME, insertable = false)
    private String lastModifiedBy;

    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return Objects.isNull(this.createdDate)
            ? Optional.empty()
            : Optional.of(LocalDateTime.ofInstant(this.createdDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public void setCreatedDate(@NonNull final LocalDateTime createdDate) {
        this.createdDate = Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return Objects.isNull(this.lastModifiedDate)
            ? Optional.empty()
            : Optional.of(LocalDateTime.ofInstant(this.lastModifiedDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public void setLastModifiedDate(@NonNull final LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = Date.from(lastModifiedDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }

    @PrePersist
    protected void onCreate() {
        setCreatedBy(this.getClass().getName());
    }

    @PreUpdate
    protected void onUpdate() {
        setLastModifiedBy(this.getClass().getName());
    }
}
