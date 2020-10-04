package com.sensiblemetrics.api.customiere.crm.clients.repository;

import com.sensiblemetrics.api.customiere.crm.clients.model.entity.DeviceEntity;
import com.sensiblemetrics.api.customiere.metrics.annotation.MonitoredRepository;

import java.util.UUID;

/**
 * {@link DeviceEntity} {@link BaseRepository} declaration
 */
@MonitoredRepository
public interface DeviceRepository extends BaseRepository<DeviceEntity, UUID> {
}
