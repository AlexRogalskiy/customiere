package com.sensiblemetrics.api.customiere.crm.clients.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.sensiblemetrics.api.customiere.crm.clients.property.RestApiProperty;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;

import java.util.List;

@Configuration
@EnableConfigurationProperties(RestApiProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebService Mapper configuration")
public class WebServiceMapperConfiguration {

    @Bean
    @ConditionalOnProperty(value = "spring.jackson.date-format", matchIfMissing = true, havingValue = "none")
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.featuresToEnable(
                JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS,
                JsonWriteFeature.QUOTE_FIELD_NAMES
            );
            builder.featuresToDisable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS
            );
            builder.indentOutput(true);
            builder.autoDetectFields(true);
            builder.createXmlMapper(false);
        };
    }

    @Bean
    @Description("Default model mapper configuration bean")
    public ModelMapper modelMapper(final List<Converter<?, ?>> converters,
                                   final List<PropertyMap<?, ?>> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
            .setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC)
            .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
            .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
            .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
            .setAmbiguityIgnored(true)
            .setSkipNullEnabled(true)
            .setFieldMatchingEnabled(true)
            .setFullTypeMatchingRequired(true)
            .setImplicitMappingEnabled(true);
        converters.forEach(modelMapper::addConverter);
        propertyMaps.forEach(modelMapper::addMappings);
        return modelMapper;
    }
}
