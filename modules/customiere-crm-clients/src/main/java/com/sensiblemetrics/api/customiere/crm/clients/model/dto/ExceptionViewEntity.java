package com.sensiblemetrics.api.customiere.crm.clients.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static com.sensiblemetrics.api.customiere.crm.clients.model.dto.ExceptionViewEntity.Info.*;

@Builder
@Data
@Validated
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(
    value = {
        TIMESTAMP_FIELD_NAME,
        STATUS_FIELD_NAME,
        ERROR_FIELD_NAME,
        MESSAGE_FIELD_NAME,
        PATH_FIELD_NAME
    },
    alphabetic = true
)
@ApiModel(value = VIEW_ID, description = "Provide details on exception view attributes")
public class ExceptionViewEntity {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -328743858984114195L;

    @ApiModelProperty(value = "Response path", name = PATH_FIELD_NAME, example = "/user/1", required = true)
    @JsonProperty(value = PATH_FIELD_NAME, required = true)
    @NotBlank(message = "Response <path> attribute should not be blank")
    private final String path;

    @ApiModelProperty(value = "Response status", name = STATUS_FIELD_NAME, example = "500", required = true)
    @JsonProperty(value = STATUS_FIELD_NAME, required = true)
    @PositiveOrZero(message = "Response <status> attribute should be positive number")
    private final Integer status;

    @ApiModelProperty(value = "Response error", name = ERROR_FIELD_NAME, example = "Internal Server Error", required = true)
    @JsonProperty(value = ERROR_FIELD_NAME, required = true)
    @NotBlank(message = "Response <error> attribute should not be blank")
    private final String error;

    @ApiModelProperty(value = "Response message", name = MESSAGE_FIELD_NAME, example = "Incorrect result size: expected 1, actual 0", required = true)
    @JsonProperty(value = MESSAGE_FIELD_NAME, required = true)
    @NotBlank(message = "Response <message> attribute should not be blank")
    private final String message;

    @ApiModelProperty(value = "Response timestamp", name = TIMESTAMP_FIELD_NAME, example = "1551372385400", required = true)
    @JsonProperty(value = TIMESTAMP_FIELD_NAME, required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ", locale = "en_GB")
    @NotNull(message = "Response <timestamp> attribute should not be null")
    private final LocalDateTime timestamp;

    /**
     * Returns {@link ExceptionViewEntity} by input parameters
     *
     * @param path    - initial input path {@link String}
     * @param message - initial input message {@link String}
     * @param status  - initial input code {@link HttpStatus}
     * @return {@link ExceptionViewEntity}
     */
    public static ExceptionViewEntity of(final String path,
                                         final String message,
                                         final HttpStatus status) {
        return ExceptionViewEntity.builder()
            .path(path)
            .message(message)
            .error(status.name())
            .status(status.value())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @UtilityClass
    public static class Info {
        /**
         * Default view ID
         */
        static final String VIEW_ID = "exceptionView";

        /**
         * Default field names
         */
        static final String PATH_FIELD_NAME = "path";
        static final String STATUS_FIELD_NAME = "code";
        static final String ERROR_FIELD_NAME = "description";
        static final String MESSAGE_FIELD_NAME = "message";
        static final String TIMESTAMP_FIELD_NAME = "timestamp";
    }
}
