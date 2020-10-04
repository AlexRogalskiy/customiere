package com.sensiblemetrics.api.customiere.crm.clients.service.interfaces;

import com.sensiblemetrics.api.customiere.crm.clients.repository.BaseRepository;

import java.io.Serializable;

/**
 * Repository aware interface declaration
 *
 * @param <E>  type of entity model
 * @param <ID> type of entity model {@link Serializable} identifier
 */
@FunctionalInterface
public interface RepositoryAware<E, ID extends Serializable> {
    /**
     * Returns {@link BaseRepository}
     *
     * @return {@link BaseRepository}
     */
    BaseRepository<E, ID> getRepository();
}
