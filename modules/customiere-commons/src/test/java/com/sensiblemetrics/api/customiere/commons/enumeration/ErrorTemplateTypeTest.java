package com.sensiblemetrics.api.customiere.commons.enumeration;

import com.sensiblemetrics.api.customiere.commons.internal.annotation.SimpleTest;
import com.sensiblemetrics.api.customiere.commons.internal.annotation.VariableSource;
import com.sensiblemetrics.api.customiere.commons.internal.configuration.TestTag;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SimpleTest
@Tag(TestTag.ENUMERATION)
class ErrorTemplateTypeTest {

    private static final Stream<Arguments> errorTemplateTypeValues = Stream.of(
        Arguments.of(ErrorTemplateType.INVALID_FORMAT, createTypeMatcher("error-0010", "error.incorrect.format")),
        Arguments.of(ErrorTemplateType.EMPTY_CONTENT, createTypeMatcher("error-0020", "error.no.content")),
        Arguments.of(ErrorTemplateType.SERVICE_UNAVAILABLE, createTypeMatcher("error-0030", "error.service.unavailable")),
        Arguments.of(ErrorTemplateType.SERVICE_OPERATION_ERROR, createTypeMatcher("error-0040", "error.operation.invalid")),
        Arguments.of(ErrorTemplateType.BAD_REQUEST, createTypeMatcher("error-0050", "error.request.invalid")),
        Arguments.of(ErrorTemplateType.INVALID_DATA, createTypeMatcher("error-0060", "error.service.data.invalid")),
        Arguments.of(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION, createTypeMatcher("error-0070", "error.endpoint.configuration.invalid")),
        Arguments.of(ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION, createTypeMatcher("error-0080", "error.endpoint.security.configuration.invalid")),
        Arguments.of(ErrorTemplateType.INVALID_PARAMETER, createTypeMatcher("error-0090", "error.parameter.invalid")),
        Arguments.of(ErrorTemplateType.RESOURCE_ALREADY_EXIST, createTypeMatcher("error-0100", "error.resource.exist")),
        Arguments.of(ErrorTemplateType.RESOURCES_ALREADY_EXIST, createTypeMatcher("error-0110", "error.resources.exist"))
    );

    @ParameterizedTest
    @VariableSource("errorTemplateTypeValues")
    void testCheckErrorTemplateTypeByCodeAndDescription(final ErrorTemplateType enumType,
                                                        final Matcher<ErrorTemplateType> matcher) {
        // then
        assertThat(enumType, matcher);
    }

    @ParameterizedTest
    @MethodSource
    void testCheckErrorTemplateTypeByName(final ErrorTemplateType enumType,
                                          final Matcher<String> matcher) {
        // then
        assertThat(enumType.getErrorCode(), matcher);
    }

    private static Stream<Arguments> testCheckErrorTemplateTypeByName() {
        return Stream.of(
            Arguments.of(ErrorTemplateType.INVALID_FORMAT, equalTo("error-0010")),
            Arguments.of(ErrorTemplateType.EMPTY_CONTENT, equalTo("error-0020")),
            Arguments.of(ErrorTemplateType.SERVICE_UNAVAILABLE, equalTo("error-0030")),
            Arguments.of(ErrorTemplateType.SERVICE_OPERATION_ERROR, equalTo("error-0040")),
            Arguments.of(ErrorTemplateType.BAD_REQUEST, equalTo("error-0050")),
            Arguments.of(ErrorTemplateType.INVALID_DATA, equalTo("error-0060")),
            Arguments.of(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION, equalTo("error-0070")),
            Arguments.of(ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION, equalTo("error-0080")),
            Arguments.of(ErrorTemplateType.INVALID_PARAMETER, equalTo("error-0090")),
            Arguments.of(ErrorTemplateType.RESOURCE_ALREADY_EXIST, equalTo("error-0100")),
            Arguments.of(ErrorTemplateType.RESOURCES_ALREADY_EXIST, equalTo("error-0110"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCheckErrorTemplateTypeByValue(final String enumName,
                                           final Matcher<ErrorTemplateType> matcher) {
        // then
        assertThat(EnumUtils.getEnum(ErrorTemplateType.class, enumName), matcher);
    }

    private static Stream<Arguments> testCheckErrorTemplateTypeByValue() {
        return Stream.of(
            Arguments.of("INVALID_FORMAT", equalTo(ErrorTemplateType.INVALID_FORMAT)),
            Arguments.of("EMPTY_CONTENT", equalTo(ErrorTemplateType.EMPTY_CONTENT)),
            Arguments.of("SERVICE_UNAVAILABLE", equalTo(ErrorTemplateType.SERVICE_UNAVAILABLE)),
            Arguments.of("SERVICE_OPERATION_ERROR", equalTo(ErrorTemplateType.SERVICE_OPERATION_ERROR)),
            Arguments.of("BAD_REQUEST", equalTo(ErrorTemplateType.BAD_REQUEST)),
            Arguments.of("INVALID_DATA", equalTo(ErrorTemplateType.INVALID_DATA)),
            Arguments.of("INVALID_ENDPOINT_CONFIGURATION", equalTo(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION)),
            Arguments.of("INVALID_ENDPOINT_SECURITY_CONFIGURATION", equalTo(ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION)),
            Arguments.of("INVALID_PARAMETER", equalTo(ErrorTemplateType.INVALID_PARAMETER)),
            Arguments.of("RESOURCE_ALREADY_EXIST", equalTo(ErrorTemplateType.RESOURCE_ALREADY_EXIST)),
            Arguments.of("RESOURCES_ALREADY_EXIST", equalTo(ErrorTemplateType.RESOURCES_ALREADY_EXIST))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCheckErrorTemplateTypeByValueOfWithClass(final String enumName,
                                                      final Matcher<ErrorTemplateType> matcher) {
        // then
        assertThat(ErrorTemplateType.valueOf(ErrorTemplateType.class, enumName), matcher);
    }

    private static Stream<Arguments> testCheckErrorTemplateTypeByValueOfWithClass() {
        return Stream.of(
            Arguments.of("INVALID_FORMAT", equalTo(ErrorTemplateType.INVALID_FORMAT)),
            Arguments.of("EMPTY_CONTENT", equalTo(ErrorTemplateType.EMPTY_CONTENT)),
            Arguments.of("SERVICE_UNAVAILABLE", equalTo(ErrorTemplateType.SERVICE_UNAVAILABLE)),
            Arguments.of("SERVICE_OPERATION_ERROR", equalTo(ErrorTemplateType.SERVICE_OPERATION_ERROR)),
            Arguments.of("BAD_REQUEST", equalTo(ErrorTemplateType.BAD_REQUEST)),
            Arguments.of("INVALID_DATA", equalTo(ErrorTemplateType.INVALID_DATA)),
            Arguments.of("INVALID_ENDPOINT_CONFIGURATION", equalTo(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION)),
            Arguments.of("INVALID_ENDPOINT_SECURITY_CONFIGURATION", equalTo(ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION)),
            Arguments.of("INVALID_PARAMETER", equalTo(ErrorTemplateType.INVALID_PARAMETER)),
            Arguments.of("RESOURCE_ALREADY_EXIST", equalTo(ErrorTemplateType.RESOURCE_ALREADY_EXIST)),
            Arguments.of("RESOURCES_ALREADY_EXIST", equalTo(ErrorTemplateType.RESOURCES_ALREADY_EXIST))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCheckErrorTemplateTypeWhenPassedCode(final String enumCode,
                                                  final Matcher<ErrorTemplateType> matcher) {
        // then
        assertThat(ErrorTemplateType.findByCode(enumCode), matcher);
    }

    private static Stream<Arguments> testCheckErrorTemplateTypeWhenPassedCode() {
        return Stream.of(
            Arguments.of("error-0010", equalTo(ErrorTemplateType.INVALID_FORMAT)),
            Arguments.of("error-0020", equalTo(ErrorTemplateType.EMPTY_CONTENT)),
            Arguments.of("error-0030", equalTo(ErrorTemplateType.SERVICE_UNAVAILABLE)),
            Arguments.of("error-0040", equalTo(ErrorTemplateType.SERVICE_OPERATION_ERROR)),
            Arguments.of("error-0050", equalTo(ErrorTemplateType.BAD_REQUEST)),
            Arguments.of("error-0060", equalTo(ErrorTemplateType.INVALID_DATA)),
            Arguments.of("error-0070", equalTo(ErrorTemplateType.INVALID_ENDPOINT_CONFIGURATION)),
            Arguments.of("error-0080", equalTo(ErrorTemplateType.INVALID_ENDPOINT_SECURITY_CONFIGURATION)),
            Arguments.of("error-0090", equalTo(ErrorTemplateType.INVALID_PARAMETER)),
            Arguments.of("error-0100", equalTo(ErrorTemplateType.RESOURCE_ALREADY_EXIST)),
            Arguments.of("error-0110", equalTo(ErrorTemplateType.RESOURCES_ALREADY_EXIST))
        );
    }

    @Test
    void testCheckErrorTemplateType_whenPassedNonExistedType() {
        // given
        final String errorMessage = "No enum constant";

        // when
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> ErrorTemplateType.valueOf("NON_EXISTED")
        );

        // then
        assertThat(thrown.getMessage(), startsWith(errorMessage));
    }

    private static CustomTypeSafeMatcher<ErrorTemplateType> createTypeMatcher(final String errorCode,
                                                                              final String errorDescription) {
        return new CustomTypeSafeMatcher<ErrorTemplateType>(format("Comparing error code={%s} and description message={%s}", errorCode, errorDescription)) {
            /**
             * Subclasses should implement this. The item will already have been checked for
             * the specific type and will never be null.
             *
             * @param item - initial input {@link ErrorTemplateType} to operate by
             */
            @Override
            protected boolean matchesSafely(final ErrorTemplateType item) {
                return StringUtils.equals(item.getErrorCode(), errorCode)
                    && StringUtils.equals(item.getErrorMessage(), errorDescription);
            }
        };
    }

    private static Function<ErrorTemplateType, CustomTypeSafeMatcher<ErrorTemplateType>> createTypeMatcher() {
        return value -> new CustomTypeSafeMatcher<ErrorTemplateType>(format("Comparing error template type={%s}", value)) {
            /**
             * Subclasses should implement this. The item will already have been checked for
             * the specific type and will never be null.
             *
             * @param item - initial input {@link ErrorTemplateType} to operate by
             */
            @Override
            protected boolean matchesSafely(final ErrorTemplateType item) {
                return item.ordinal() == value.ordinal()
                    && StringUtils.equals(item.name(), value.name())
                    && StringUtils.equals(item.getErrorCode(), value.getErrorCode())
                    && StringUtils.equals(item.getErrorMessage(), value.getErrorMessage());
            }
        };
    }
}
