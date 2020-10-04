package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@ToString
public class GeolocationEntity implements Serializable {

    @BigDecimalRange(minPrecision = 1, maxPrecision = 10, scale = 2, message = "location.latitude.range")
    private BigDecimal latitude;

    @BigDecimalRange(minPrecision = 1, maxPrecision = 10, scale = 2, message = "location.longitude.range")
    private BigDecimal longitude;

    public GeolocationEntity() {
        this(0.0, 0.0);
    }

    public GeolocationEntity(double latitude, double longitude) {
        this(BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
    }

    public GeolocationEntity(final BigDecimal latitude, final BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
