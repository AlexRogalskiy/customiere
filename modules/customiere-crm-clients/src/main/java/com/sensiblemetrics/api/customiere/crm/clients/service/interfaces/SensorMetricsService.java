//package com.sensiblemetrics.api.customiere.crm.clients.service.interfaces;
//
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataSearchRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorMetricsPostRequest;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorRecordEntity;
//import org.springframework.data.domain.Pageable;
//import reactor.core.publisher.Flux;
//
//import java.util.UUID;
//
//public interface SensorMetricsService extends BaseModelService<SensorRecordEntity, Long> {
//
//    /**
//     * Default service ID
//     */
//    String SERVICE_ID = "sensorMetricsService";
//
//    Flux<SensorRecordEntity> findBySensorId(final UUID id);
//
//    Flux<SensorRecordEntity> findBySensorName(final String name);
//
//    Flux<SensorRecordEntity> findByRequest(final SensorDataGetRequest request);
//
//    Flux<SensorRecordEntity> storeMetricsData(final SensorMetricsPostRequest request);
//
//    Flux<SensorRecordEntity> findByIds(final Iterable<Long> ids);
//
//    Flux<SensorRecordEntity> findBySearchRequest(final SensorDataSearchRequest request);
//
//    Flux<SensorRecordEntity> findBySensorIds(final Iterable<UUID> ids, final Pageable page);
//
//    Flux<SensorRecordEntity> findAllBySensorNameAndLocation(final String name, final GeolocationEntity location);
//}
