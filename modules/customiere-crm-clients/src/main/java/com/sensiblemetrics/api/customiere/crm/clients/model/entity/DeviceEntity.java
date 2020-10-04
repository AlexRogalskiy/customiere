package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.sensiblemetrics.api.customiere.validation.constraint.ConstraintGroup;
import com.sensiblemetrics.api.customiere.validation.constraint.NamePattern;
import com.sensiblemetrics.api.customiere.crm.clients.enumeration.ClientStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

import static com.sensiblemetrics.api.customiere.crm.clients.model.entity.DeviceEntity.Info.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = MODEL_NAME)
@Table(name = TABLE_NAME)
@BatchSize(size = 10)
@TypeDef(name = "status_type_enum", typeClass = StatusEnumType.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CACHE_NAME)
public class DeviceEntity extends BaseAuditEntity<UUID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1591260887009630056L;

    @NamePattern(message = "{model.entity.device.company.pattern}")
    @Column(name = COMPANY_FIELD_NAME, nullable = false, length = 256)
    private String company;

    @NamePattern(message = "{model.entity.device.partner.pattern}")
    @Column(name = PARTNER_FIELD_NAME, nullable = false, length = 256)
    private String partner;

    @NamePattern(message = "{model.entity.device.product.pattern}")
    @Column(name = PRODUCT_FIELD_NAME, nullable = false, length = 256)
    private String product;

    @PositiveOrZero(message = "{model.entity.device.code.positiveOrZero}")
    @Column(name = CODE_FIELD_NAME, nullable = false)
    private Integer code;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @NotBlank(message = "{model.entity.device.data.notBlank}")
    @Column(name = DATA_FIELD_NAME, nullable = false, length = 1024)
    private String data;

    @Null(groups = ConstraintGroup.OnCreate.class, message = "{model.entity.device.status.null}")
    @NotNull(groups = {
            ConstraintGroup.OnUpdate.class,
            ConstraintGroup.OnSelect.class,
            ConstraintGroup.OnDelete.class
    }, message = "{model.entity.device.status.notNull}")
    @Enumerated(EnumType.STRING)
    //@Type(type = "status_type_enum")
    @Convert(converter = ClientStatusTypeConverter.class)
    @Column(name = STATUS_FIELD_NAME, columnDefinition = "status_enum", nullable = false, length = 64)
    private ClientStatusType status;

    @UtilityClass
    public static final class Info {
        /**
         * Default model ID
         */
        public static final String MODEL_NAME = "Document";
        /**
         * Default table name
         */
        static final String TABLE_NAME = "devices";
        static final String CACHE_NAME = "deviceCache";
        /**
         * Default field names
         */
        static final String COMPANY_FIELD_NAME = "company";
        static final String PARTNER_FIELD_NAME = "partner";
        static final String PRODUCT_FIELD_NAME = "product";
        static final String CODE_FIELD_NAME = "code";
        static final String DATA_FIELD_NAME = "data";
        static final String STATUS_FIELD_NAME = "status";
    }
}
