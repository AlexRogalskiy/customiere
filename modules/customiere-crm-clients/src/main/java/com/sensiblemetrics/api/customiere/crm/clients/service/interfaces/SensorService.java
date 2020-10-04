//package com.sensiblemetrics.api.customiere.crm.clients.service.interfaces;
//
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataRequest;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorRecordEntity;
//import org.springframework.data.domain.Pageable;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//public interface SensorService extends BaseService<SensorEntity, UUID> {
//
//    /**
//     * Default service ID
//     */
//    String SERVICE_ID = "sensorService";
//
//    Flux<SensorEntity> findByLocation(final GeolocationEntity geolocationEntity);
//
//    Flux<SensorRecordEntity> findMetricsById(final UUID sensorId, final Pageable page);
//
//    Flux<SensorEntity> findByNameAndLocation(final String name, final GeolocationEntity location);
//
//    Flux<SensorEntity> findByRequest(final SensorDataGetRequest request);
//
//    Mono<SensorEntity> storeSensorData(final SensorDataRequest request);
//}
