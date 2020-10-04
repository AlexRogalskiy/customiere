//package com.sensiblemetrics.api.customiere.crm.clients.service.impl;
//
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.OffsetPageRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataRequest;
//import com.siemens.microservices.sonarine.sensors.exception.ResourceAlreadyExistException;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorRecordEntity;
//import com.siemens.microservices.sonarine.sensors.repository.SensorRepository;
//import com.siemens.microservices.sonarine.sensors.service.interfaces.SensorService;
//import com.siemens.microservices.sonarine.sensors.validation.annotation.SensorDataValidation;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//
//import static com.siemens.microservices.sonarine.sensors.utility.ServiceUtils.getItems;
//
//@Slf4j
//@Getter(AccessLevel.PROTECTED)
//@Service(SensorService.SERVICE_ID)
//@RequiredArgsConstructor
//@Transactional(rollbackFor = Exception.class)
//public class SensorServiceImpl extends BaseServiceImpl<SensorEntity, UUID> implements SensorService {
//
//    private final SensorRepository sensorRepository;
//    private final ConversionService conversionService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorEntity> findByLocation(final GeolocationEntity location) {
//        return getRepository().findAllByLocationReactive(location);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findMetricsById(final UUID sensorId, final Pageable page) {
//        return getRepository().findAllMetricsByIdReactive(sensorId, page);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorEntity> findByNameAndLocation(final String name, final GeolocationEntity location) {
//        return getRepository().findAllByNameAndLocationReactive(name, location);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorEntity> findByRequest(final SensorDataGetRequest request) {
//        final List<UUID> items = getItems(request, UUID::fromString);
//        if (Objects.nonNull(request.getPage())) {
//            return Flux.fromIterable(getRepository().findByIdInAll(items, new OffsetPageRequest(request.getPage().getOffset(), request.getPage().getLimit(), this.buildSortOrder(request.getPage().getSort()))));
//        }
//        return findAll(items);
//    }
//
//    @Override
//    @SensorDataValidation
//    public Mono<SensorEntity> storeSensorData(@Validated final SensorDataRequest request) {
//        getRepository().existsById(request.getSensorId()).doOnError((Throwable e) -> ResourceAlreadyExistException.throwResourceExist(getMessageSource(), request.getSensorId(), e));
//        final GeolocationEntity location = getConversionService().convert(request.getCoordinates(), GeolocationEntity.class);
//        final SensorEntity sensorEntity = new SensorEntity();
//        sensorEntity.setStatus(request.getStatus());
//        sensorEntity.setName(request.getName());
//        sensorEntity.setDescription(request.getDescription());
//        sensorEntity.setLocation(location);
//        return getRepository().save(sensorEntity);
//    }
//
//    @Override
//    protected SensorRepository getRepository() {
//        return this.sensorRepository;
//    }
//}
