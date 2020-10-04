package com.sensiblemetrics.api.customiere.crm.clients.model.converter;

import org.apache.commons.lang3.EnumUtils;

import javax.persistence.AttributeConverter;
import java.util.Optional;

import static com.sensiblemetrics.api.customiere.commons.utils.ServiceUtils.getClassGenericType;

public abstract class EnumTypeConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

    @Override
    public String convertToDatabaseColumn(final E statusType) {
        return Optional.ofNullable(statusType).map(Enum::name).orElse(null);
    }

    @Override
    public E convertToEntityAttribute(final String value) {
        return EnumUtils.getEnum(getClassGenericType(this.getClass()), value);
    }
}
