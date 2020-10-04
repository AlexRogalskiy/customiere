package com.sensiblemetrics.api.customiere.crm.clients.service.interfaces;

import com.sensiblemetrics.api.customiere.validation.constraint.ConstraintGroup;
import com.sensiblemetrics.api.customiere.crm.clients.model.entity.DeviceEntity;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Device {@link BaseService} declaration
 */
public interface DeviceService extends BaseService<DeviceEntity, UUID> {
    /**
     * Returns created {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return created {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnCreate.class)
    Mono<DeviceEntity> createDevice(final DeviceEntity deviceEntity);

    /**
     * Returns fetched {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return fetched {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnSelect.class)
    Mono<DeviceEntity> findDevice(final DeviceEntity deviceEntity);

    /**
     * Returns deleted {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return deleted {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnDelete.class)
    Mono<DeviceEntity> deleteDevice(final DeviceEntity deviceEntity);

    /**
     * Returns updated {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return updated {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnUpdate.class)
    Mono<DeviceEntity> updateDevice(final DeviceEntity deviceEntity);
}
