package com.sensiblemetrics.api.customiere.crm.clients.repository;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.QueryHint;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_READONLY;

/**
 * Base {@link R2dbcRepository} repository declaration
 *
 * @param <E>  type of {@link Serializable} entity
 * @param <ID> type of {@link Serializable} entity identifier {@link Serializable}
 */
@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends R2dbcRepository<E, ID>, ReactiveSortingRepository<E, ID>, ReactiveQueryByExampleExecutor<E> {//}, JpaSpecificationExecutor<E> {

    /**
     * Returns collection of {@link Serializable} models as {@link Stream} by query
     *
     * @param <S> type of {@link Serializable} entity
     * @return {@link Stream} of {@link Serializable} models
     */
    @Async
    @Query(RepositoryQuery.FIND_ALL)
    @QueryHints({@QueryHint(name = HINT_READONLY, value = "true"), @QueryHint(name = CACHEABLE, value = "true")})
    <S extends E> CompletableFuture<Stream<S>> streamAll();

    /**
     * Base repository entity queries
     */
    @UtilityClass
    class RepositoryQuery {
        /**
         * Default query to fetch all {@link Serializable} models
         */
        static final String FIND_ALL = "SELECT e FROM #{#entityName} e";
    }
}
