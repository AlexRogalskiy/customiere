package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.google.common.collect.Lists;
import com.siemens.microservices.sonarine.sensors.annotation.ApiVersion;
import com.siemens.microservices.sonarine.sensors.controller.iface.BaseJpaController;
import com.siemens.microservices.sonarine.sensors.enumeration.VersionStatusType;
import com.siemens.microservices.sonarine.sensors.exception.EmptyContentException;
import com.siemens.microservices.sonarine.sensors.exception.ResourceAlreadyExistException;
import com.siemens.microservices.sonarine.sensors.exception.ResourceNotFoundException;
import com.siemens.microservices.sonarine.sensors.service.interfaces.BaseJpaService;
import com.siemens.microservices.sonarine.sensors.utility.MapperUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@link BaseJpaController} implementation
 *
 * @param <E>  type of model {@link Serializable}
 * @param <T>  type of view model {@link Serializable}
 * @param <ID> type of model identifier {@link Serializable}
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@ApiVersion(VersionStatusType.SNAPSHOT)
public abstract class BaseJpaControllerImpl<E extends Serializable, T extends Serializable, ID extends Serializable> implements BaseJpaController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Scheduler jdbcScheduler;

    protected Flux<E> getAllItems() {
        return this.getAllItems(Optional.empty());
    }

    protected Flux<E> getAllItems(final Optional<Integer> limit) {
        log.debug("Fetching all items");
        if (limit.isPresent()) {
            return this.getService().findAll().take(limit.get()).onBackpressureBuffer();
        }
        return this.getService().findAll().onBackpressureBuffer();
    }

    protected Flux<E> getItemsByIds(final Iterable<ID> ids) {
        log.debug("Fetching items by IDs: {}", StringUtils.join(ids, "|"));
        return this.getService().findAll(ids).onBackpressureBuffer().share();
    }

    protected List<? extends E> getItemsList(final Iterable<? extends E> items) throws EmptyContentException {
        log.debug("Fetching items: {}", StringUtils.join(items, "|"));
        final List<? extends E> itemsList = Lists.newArrayList(items);
        if (itemsList.isEmpty()) {
            throw EmptyContentException.throwEmptyContent(getMessageSource());
        }
        return itemsList;
    }

    protected Mono<E> getItemById(final ID id) {
        log.debug("Fetching item by ID: {}", id);
        return getService().findById(id).subscribeOn(getJdbcScheduler()).doOnError((Throwable e) -> ResourceNotFoundException.throwResourceNotFound(getMessageSource(), id, e));
    }

    protected Mono<E> createItem(final T itemDto, final Class<? extends E> entityClass) {
        log.debug("Creating new item: {}", itemDto);
        final E itemEntity = MapperUtils.map(itemDto, entityClass);
        if (getService().exists(itemEntity).block()) {
            throw ResourceAlreadyExistException.throwResourceExist(getMessageSource(), itemEntity, null);
        }
        return getService().save(itemEntity);
    }

    protected Mono<E> updateItem(final ID id, final T itemDto, final Class<? extends E> entityClass) {
        log.info("Updating item by ID: {}, itemDto: {}", id, itemDto);
        final Mono<E> currentItem = getService().findById(id).doOnError((Throwable e) -> ResourceNotFoundException.throwResourceNotFound(getMessageSource(), id, e));
        final E itemEntity = MapperUtils.map(itemDto, entityClass);
        //copyProperties(target, currentItem, properties);
        return getService().save(itemEntity);
    }

    protected Mono<E> deleteItem(final ID id) {
        log.info("Deleting item by ID: {}", id);
        final Mono<E> item = getService().findById(id).doOnError((Throwable e) -> ResourceNotFoundException.throwResourceNotFound(getMessageSource(), id, e));
        getService().delete(item.block());
        return item;
    }

    protected void deleteItems(final List<? extends T> itemDtos, final Class<? extends E> entityClass) {
        log.debug("Deleting items: {}", StringUtils.join(itemDtos, ", "));
        getService().deleteAll(MapperUtils.mapAll(itemDtos, entityClass));
    }

    protected void deleteAllItems() {
        log.debug("Deleting all items");
        getService().deleteAll();
    }

    protected HttpHeaders getHeaders(final Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(DEFAULT_TOTAL_ELEMENTS_HEADER, Long.toString(page.getTotalElements()));
        headers.add(DEFAULT_EXPIRES_AFTER_HEADER, LocalDate.now().plusDays(DEFAULT_EXPIRE_PERIOD_IN_DAYS).toString());
        headers.add(DEFAULT_RATE_LIMIT_HEADER, String.valueOf(DEFAULT_RATE_LIMIT));
        return headers;
    }

    /**
     * Base enum converter implementation {@link PropertyEditorSupport}
     *
     * @param <U> type of enum value
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @RequiredArgsConstructor
    protected static class BaseEnumConverter<U extends Enum<U>> extends PropertyEditorSupport {

        /**
         * Default enum {@link Class} type
         */
        private final Class<U> type;

        @Override
        public void setAsText(final String text) {
            final U item = Enum.valueOf(getType(), text.toUpperCase());
            this.setValue(item);
        }
    }

    /**
     * Returns {@link BaseJpaService} service
     *
     * @return {@link BaseJpaService} service
     */
    protected abstract BaseJpaService<E, ID> getService();
}
