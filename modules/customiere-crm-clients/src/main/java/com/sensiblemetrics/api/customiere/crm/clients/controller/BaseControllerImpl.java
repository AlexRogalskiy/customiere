package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.siemens.microservices.sonarine.sensors.annotation.ApiVersion;
import com.siemens.microservices.sonarine.sensors.controller.iface.BaseController;
import com.siemens.microservices.sonarine.sensors.enumeration.VersionStatusType;
import com.siemens.microservices.sonarine.sensors.exception.ResourceNotFoundException;
import com.siemens.microservices.sonarine.sensors.model.dao.entity.AbstractBaseEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.domain.AbstractBaseViewEntity;
import com.siemens.microservices.sonarine.sensors.service.interfaces.BaseService;
import com.siemens.microservices.sonarine.sensors.utility.MapperUtils;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;

import static com.siemens.microservices.sonarine.sensors.utility.StringUtils.formatMessage;

/**
 * {@link BaseController} implementation
 *
 * @param <E>  type of model {@link AbstractBaseEntity}
 * @param <T>  type of model view {@link AbstractBaseViewEntity}
 * @param <ID> type of model identifier {@link Serializable}
 */
@Slf4j
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiVersion(VersionStatusType.SNAPSHOT)
public abstract class BaseControllerImpl<E extends AbstractBaseEntity<ID>, T extends AbstractBaseViewEntity<ID>, ID extends Serializable> extends AuditControllerImpl<E, T, ID> implements BaseController<E, T, ID> {

    /**
     * Returns updated {@code E} item stream by initial input entity identifier {@code ID}, dto item {@code T} and entity {@link Class}
     *
     * @param id          - initial input entity identifier {@code ID}
     * @param itemDto     - initial input dto item {@code T}
     * @param entityClass - initial input entity {@link Class}
     * @return updated {@code E} item stream
     */
    @Override
    public Mono<E> updateItem(final ID id, final T itemDto, final Class<? extends E> entityClass) {
        final Mono<E> currentItem = getService().findById(id).doOnError((Throwable e) -> new ResourceNotFoundException(formatMessage(getMessageSource(), "error.no.item.id", id), e));
        final E itemEntity = MapperUtils.map(itemDto, entityClass);
        getService().saveOrUpdate(itemEntity, entityClass);
        return currentItem;
    }

    /**
     * Returns delayed item {@code E} {@link Mono} stream by initial input {@link Mono} instance and duration value
     *
     * @param mono          - initial input {@link Mono} stream
     * @param delayInMillis - initial input delay in millis
     * @return delayed item {@code E} {@link Mono} stream
     */
    protected Mono<E> withDelay(final Mono<E> mono, long delayInMillis) {
        return Mono
            .delay(Duration.ofMillis(delayInMillis))
            .flatMap(c -> mono);
    }

    /**
     * Returns delayed item {@code E} {@link Flux} stream by initial input {@link Flux} instance and duration value
     *
     * @param userFlux      - initial input {@link Flux} stream
     * @param delayInMillis - initial input delay in millis
     * @return delayed item {@code E} {@link Mono} stream
     */
    protected Flux<E> withDelay(final Flux<E> userFlux, long delayInMillis) {
        return Flux
            .interval(Duration.ofMillis(delayInMillis))
            .zipWith(userFlux, (i, item) -> item);
    }

    /**
     * Return {@link Flux} wrapper by initial input {@link Iterable} collection
     *
     * @param iterable - initial input {@link Iterable} collection
     * @return {@link Flux} wrapper
     */
    protected Flux<E> getFluxStream(final Iterable<E> iterable) {
        return Flux.fromIterable(iterable)
            .onBackpressureBuffer()
            .share();
    }

    /**
     * Returns {@link BaseService} service
     *
     * @return {@link BaseService} service
     */
    @Override
    protected abstract BaseService<E, ID> getService();
}
