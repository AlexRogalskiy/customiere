package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OffsetPageRequest implements Pageable, Serializable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4231447474761452005L;

    /**
     * Default page offset
     */
    private long offset;
    /**
     * Default page limit
     */
    private int limit;
    /**
     * Default page sort
     */
    private Sort sort;

    /**
     * Default offset page request constructor with initial offset / limit input arguments
     *
     * @param offset - initial offset input argument
     * @param limit  - initial limit input argument
     */
    public OffsetPageRequest(long offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    /**
     * Default offset page request constructor with initial offset / limit input arguments
     *
     * @param offset - initial offset input argument
     * @param limit  - initial limit input argument
     * @param sort   - initial limit {@link Sort} argument
     */
    public OffsetPageRequest(long offset, int limit, final Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return this.limit;
    }

    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public Sort getSort() {
        if (Objects.isNull(this.sort)) {
            return Sort.unsorted();
        }
        return this.sort;
    }

    @Override
    public Pageable next() {
        return OffsetPageRequest
            .builder()
            .offset(getOffset() + getPageSize())
            .limit(getPageSize())
            .sort(getSort())
            .build();
    }

    @Override
    public Pageable previousOrFirst() {
        return OffsetPageRequest
            .builder()
            .offset(Math.max(0, getOffset() - getPageSize()))
            .limit(getPageSize())
            .sort(getSort())
            .build();
    }

    @Override
    public Pageable first() {
        return OffsetPageRequest
            .builder()
            .offset(0)
            .limit(getPageSize())
            .sort(getSort())
            .build();
    }

    @Override
    public boolean hasPrevious() {
        return getOffset() > 0;
    }
}
