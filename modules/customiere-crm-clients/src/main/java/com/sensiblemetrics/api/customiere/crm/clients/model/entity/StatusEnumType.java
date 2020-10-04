package com.sensiblemetrics.api.customiere.crm.clients.model.entity;

import com.sensiblemetrics.api.customiere.commons.helper.OptionalConsumer;
import com.sensiblemetrics.api.customiere.crm.clients.enumeration.ClientStatusType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.Types;

public class StatusEnumType extends org.hibernate.type.EnumType<ClientStatusType> {

    @Override
    public void nullSafeSet(final PreparedStatement st,
                            final Object value,
                            final int index,
                            final SharedSessionContractImplementor session)
            throws HibernateException {
        OptionalConsumer.of(value)
                .ifPresent(v -> st.setObject(index, v.toString(), Types.OTHER))
                .ifNotPresent(() -> st.setNull(index, Types.OTHER));
    }
}
