package com.sensiblemetrics.api.customiere.crm.clients.model.type;

import com.siemens.microservices.sonarine.sensors.model.dao.entity.GeolocationEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * GEO location REST Application Type
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class GeolocationType implements CompositeUserType {

    @Override
    public String[] getPropertyNames() {
        return new String[]{"latitude", "longitude"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{BigDecimalType.INSTANCE, BigDecimalType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(final Object o, final int index) throws HibernateException {
        if (Objects.isNull(o)) {
            return null;
        }
        final GeolocationEntity location = (GeolocationEntity) o;
        switch (index) {
            case 0:
                return location.getLatitude();
            case 1:
                return location.getLongitude();
            default:
                throw new HibernateException("Invalid property index=" + index);
        }
    }

    @Override
    public void setPropertyValue(Object o, int index, Object o1) throws HibernateException {
        if (Objects.isNull(o)) {
            return;
        }
        final GeolocationEntity location = (GeolocationEntity) o;
        final BigDecimal value = (BigDecimal) o1;
        switch (index) {
            case 0:
                location.setLatitude(value);
                break;
            case 1:
                location.setLongitude(value);
                break;
            default:
                throw new HibernateException("Invalid property index=" + index);
        }
    }

    @Override
    public Class<?> returnedClass() {
        return GeolocationEntity.class;
    }

    @Override
    public boolean equals(final Object o, final Object o1) throws HibernateException {
        return o.equals(o1);
    }

    @Override
    public int hashCode(final Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor sessionImplementor, final Object owner) throws HibernateException, SQLException {
        if (rs.wasNull() || names.length != 2) {
            return null;
        }
        final BigDecimal latitude = (BigDecimal) BigDecimalType.INSTANCE.get(rs, names[0], sessionImplementor);
        final BigDecimal longitude = (BigDecimal) BigDecimalType.INSTANCE.get(rs, names[1], sessionImplementor);
        return (Objects.isNull(latitude) && Objects.isNull(longitude)) ? null : new GeolocationEntity(latitude, longitude);
    }

    @Override
    public void nullSafeSet(final PreparedStatement ps, final Object o, final int index, final SharedSessionContractImplementor sessionImplementor) throws HibernateException, SQLException {
        if (Objects.isNull(o)) {
            BigDecimalType.INSTANCE.set(ps, null, index, sessionImplementor);
            BigDecimalType.INSTANCE.set(ps, null, index + 1, sessionImplementor);
        } else {
            final GeolocationEntity location = (GeolocationEntity) o;
            BigDecimalType.INSTANCE.set(ps, location.getLatitude(), index, sessionImplementor);
            BigDecimalType.INSTANCE.set(ps, location.getLongitude(), index + 1, sessionImplementor);
        }
    }

    @Override
    public Object deepCopy(final Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final Object o, final SharedSessionContractImplementor sessionImplementor) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(final Serializable cached, final SharedSessionContractImplementor sessionImplementor, final Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(final Object o, final Object o1, final SharedSessionContractImplementor sessionImplementor, final Object o2) throws HibernateException {
        return o;
    }
}
