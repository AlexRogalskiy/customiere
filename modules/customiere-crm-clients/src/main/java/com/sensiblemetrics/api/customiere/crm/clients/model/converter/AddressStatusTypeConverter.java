package com.sensiblemetrics.api.customiere.crm.clients.model.converter;

import com.sensiblemetrics.api.customiere.crm.clients.enumeration.AddressStatusType;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class AddressStatusTypeConverter extends EnumTypeConverter<AddressStatusType> {
}
