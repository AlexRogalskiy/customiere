package com.sensiblemetrics.api.customiere.crm.clients.model.converter;

import com.sensiblemetrics.api.customiere.crm.clients.enumeration.ClientStatusType;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ClientStatusTypeConverter extends EnumTypeConverter<ClientStatusType> {
}
