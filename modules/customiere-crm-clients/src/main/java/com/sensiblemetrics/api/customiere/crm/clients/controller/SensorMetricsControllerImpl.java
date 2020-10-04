package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.siemens.microservices.sonarine.sensors.annotation.ApiVersion;
import com.siemens.microservices.sonarine.sensors.annotation.SwaggerAPI;
import com.siemens.microservices.sonarine.sensors.controller.iface.SensorMetricsController;
import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorMetricsPostRequest;
import com.siemens.microservices.sonarine.sensors.enumeration.VersionStatusType;
import com.siemens.microservices.sonarine.sensors.exception.ResourceNotFoundException;
import com.siemens.microservices.sonarine.sensors.exception.ServiceException;
import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorRecordEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.domain.SensorRecordViewEntity;
import com.siemens.microservices.sonarine.sensors.service.interfaces.SensorMetricsService;
import com.siemens.microservices.sonarine.sensors.utility.ServiceUtils;
import io.swagger.annotations.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

import static com.siemens.microservices.sonarine.sensors.utility.MapperUtils.map;

/**
 * {@link SensorMetricsController} implementation
 */
@Slf4j
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RestController(SensorMetricsController.CONTROLLER_ID)
@RequestMapping(value = "/api/v0/data", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
@SwaggerAPI
@Api(
    value = "/api/v0/data",
    description = "Endpoint for sensor data operations",
    consumes = "application/json, application/xml",
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    authorizations = {
        @Authorization(value = "sensor_data_store_auth",
            scopes = {
                @AuthorizationScope(scope = "write:models", description = "modify sensor data model"),
                @AuthorizationScope(scope = "read:models", description = "read sensor data model")
            })
    })
@ApiVersion(VersionStatusType.SNAPSHOT)
@CrossOrigin(methods = {RequestMethod.DELETE}, allowedHeaders = {"Siemens-Sensor-Management-Allowed"}, exposedHeaders = {"Siemens-Sensor-Management-Exposed"}, maxAge = 900)
public class SensorMetricsControllerImpl extends BaseModelControllerImpl<SensorRecordEntity, SensorRecordViewEntity, Long> implements SensorMetricsController {

    @Autowired
    private SensorMetricsService sensorMetricsService;

    @PostMapping("/metrics")
    @ResponseBody
    @ApiOperation(
        httpMethod = "POST",
        value = "Finds metrics by IDs",
        notes = "Returns list of metrics",
        nickname = "fetchMetricsByIds",
        tags = {"fetchMetricsByIds"},
        position = 1,
        response = SensorRecordViewEntity.class,
        responseContainer = "Flux",
        consumes = "application/json, application/xml",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        responseHeaders = {
            @ResponseHeader(name = "X-Expires-After", description = "date in UTC when token expires", response = Date.class),
            @ResponseHeader(name = "X-Total-Elements", description = "total number of results in response", response = Integer.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid metrics ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> getMetricsData(@ApiParam(value = "Request to fetch metrics by", required = true, readOnly = true) @Valid @RequestBody final SensorDataGetRequest request) {
        log.info("Fetching metrics by IDs: {}", StringUtils.join(request.getItems(), "|"));
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getService().findByRequest(request).map(item -> map(item, SensorRecordViewEntity.class))))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), ServiceUtils.getItems(request, Long::valueOf), e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @PostMapping("/metrics/store")
    @ResponseBody
    @ApiOperation(
        httpMethod = "POST",
        value = "Stores metrics data",
        notes = "Save list of metrics data",
        nickname = "storeMetricsDataByIds",
        tags = {"storeMetricsDataByIds"},
        position = 1,
        response = SensorRecordViewEntity.class,
        responseContainer = "Flux",
        consumes = "application/json, application/xml",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        responseHeaders = {
            @ResponseHeader(name = "X-Expires-After", description = "date in UTC when token expires", response = Date.class),
            @ResponseHeader(name = "X-Total-Elements", description = "total number of results in response", response = Integer.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid metrics ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> storeMetricsData(@ApiParam(value = "Request to store metrics by", required = true, readOnly = true) @Valid @RequestBody final SensorMetricsPostRequest request) {
        log.info("Saving metrics data: {}", StringUtils.join(request.getItems(), "|"));
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getService().storeMetricsData(request)))
            .onErrorResume(e -> Mono.error(ServiceException.throwInternalError(getMessageSource(), request.getItems(), e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/metrics/{id}")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds metrics by ID",
        notes = "For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors",
        nickname = "fetchMetricsById",
        tags = {"fetchMetricsById"},
        position = 2,
        response = SensorRecordViewEntity.class,
        responseContainer = "Mono",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid metrics ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> getMetricsById(@ApiParam(value = "Metrics ID to fetch by", required = true, readOnly = true) @PathVariable("id") final Long id) {
        log.info("Fetching metrics by ID: {}", id);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getItemById(id).map(item -> map(item, SensorRecordViewEntity.class))))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), id, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/metrics")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds all metrics",
        notes = "Returns list of all metrics",
        nickname = "fetchAllMetrics",
        tags = {"fetchAllMetrics"},
        position = 3,
        response = SensorRecordViewEntity.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getAllMetrics(@ApiParam(value = "Limit to fetch by", readOnly = true) @RequestParam(name = "limit", required = false) final Optional<Integer> limit) {
        log.info("Fetching all metrics by limit: {}", limit);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getAllItems(limit).map(item -> map(item, SensorRecordViewEntity.class))))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @Override
    protected SensorMetricsService getService() {
        return this.sensorMetricsService;
    }
}
