//package com.sensiblemetrics.api.customiere.crm.clients.service.impl;
//
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.OffsetPageRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataSearchRequest;
//import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorMetricsPostRequest;
//import com.siemens.microservices.sonarine.sensors.exception.ResourceNotFoundException;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
//import com.siemens.microservices.sonarine.sensors.model.dto.domain.MetricsRecordViewEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorEntity;
//import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorRecordEntity;
//import com.siemens.microservices.sonarine.sensors.repository.SensorMetricsRepository;
//import com.siemens.microservices.sonarine.sensors.repository.SensorRepository;
//import com.siemens.microservices.sonarine.sensors.service.interfaces.SensorMetricsService;
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
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.siemens.microservices.sonarine.sensors.utility.ServiceUtils.getItems;
//import static com.siemens.microservices.sonarine.sensors.utility.ServiceUtils.getItemsMap;
//
//@Slf4j
//@Getter(AccessLevel.PROTECTED)
//@Service(SensorMetricsService.SERVICE_ID)
//@RequiredArgsConstructor
//@Transactional(rollbackFor = Exception.class)
//public class SensorMetricsServiceImpl extends BaseModelServiceImpl<SensorRecordEntity, Long> implements SensorMetricsService {
//
//    private final SensorMetricsRepository sensorMetricsRepository;
//    private final SensorRepository sensorRepository;
//    private final ConversionService conversionService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findBySensorId(final UUID id) {
//        return getRepository().findBySensorIdReactive(id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findBySensorName(final String name) {
//        return getRepository().findBySensorNameReactive(name);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findByRequest(final SensorDataGetRequest request) {
//        final List<Long> items = getItems(request, Long::valueOf);
//        if (Objects.nonNull(request.getPage())) {
//            return getRepository().findByIdInAllReactive(items, new OffsetPageRequest(request.getPage().getOffset(), request.getPage().getLimit(), this.buildSortOrder(request.getPage().getSort())));
//        }
//        return findAll(items);
//    }
//
//    @Override
//    @SensorDataValidation
//    public Flux<SensorRecordEntity> storeMetricsData(@Validated final SensorMetricsPostRequest request) {
//        final Iterable<SensorRecordEntity> result = Optional.ofNullable(request.getItems()).orElseGet(Collections::emptyList).stream().map(item -> {
//            final SensorEntity sensorItem = getSensorRepository().findById(item.getSensorId()).doOnError((Throwable e) -> ResourceNotFoundException.throwResourceNotFound(getMessageSource(), item.getSensorId(), e)).block();
//            final GeolocationEntity location = getConversionService().convert(item.getCoordinates(), GeolocationEntity.class);
//            final List<MetricsRecordViewEntity> metricsList = item.getMetrics().stream().map(i -> getConversionService().convert(i, MetricsRecordViewEntity.class)).collect(Collectors.toList());
//            final SensorRecordEntity recordEntity = new SensorRecordEntity();
//            recordEntity.setLocation(location);
//            recordEntity.setSensor(sensorItem);
//            recordEntity.setMetrics(metricsList);
//            return recordEntity;
//        }).collect(Collectors.toList());
//        return getRepository().saveAll(result);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findBySearchRequest(final SensorDataSearchRequest request) {
//        final Map<String, String> params = getItemsMap(request);
//        return getRepository().findBySensorNameReactive(params.get("name"));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findBySensorIds(final Iterable<UUID> ids, final Pageable page) {
//        return getRepository().findBySensorIdInAllReactive(ids, page);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findAllBySensorNameAndLocation(final String name, final GeolocationEntity location) {
//        return getRepository().findAllBySensorNameAndLocationReactive(name, location);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Flux<SensorRecordEntity> findByIds(final Iterable<Long> ids) {
//        return getRepository().findAllById(ids);
//    }
//
//    @Override
//    protected SensorMetricsRepository getRepository() {
//        return this.sensorMetricsRepository;
//    }
//}
