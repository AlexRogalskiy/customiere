package com.sensiblemetrics.api.customiere.crm.clients.controller;

import com.siemens.microservices.sonarine.sensors.annotation.ApiVersion;
import com.siemens.microservices.sonarine.sensors.annotation.SwaggerAPI;
import com.siemens.microservices.sonarine.sensors.controller.iface.SensorController;
import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataGetRequest;
import com.siemens.microservices.sonarine.sensors.controller.wrapper.SensorDataRequest;
import com.siemens.microservices.sonarine.sensors.enumeration.VersionStatusType;
import com.siemens.microservices.sonarine.sensors.exception.ResourceAlreadyExistException;
import com.siemens.microservices.sonarine.sensors.exception.ResourceNotFoundException;
import com.siemens.microservices.sonarine.sensors.model.dao.entity.SensorEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.domain.SensorRecordViewEntity;
import com.siemens.microservices.sonarine.sensors.model.dto.domain.SensorViewEntity;
import com.siemens.microservices.sonarine.sensors.service.interfaces.SensorService;
import com.siemens.microservices.sonarine.sensors.utility.ServiceUtils;
import io.swagger.annotations.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javers.core.diff.Change;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.Valid;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.siemens.microservices.sonarine.sensors.utility.MapperUtils.map;

/**
 * {@link SensorController} implementation
 */
@Slf4j
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RestController(SensorController.CONTROLLER_ID)
@RequestMapping(value = "/api/v0/data", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
@SwaggerAPI
@Api(
    value = "/api/v0/data",
    description = "Endpoint for sensor operations",
    consumes = "application/json, application/xml",
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    authorizations = {
        @Authorization(value = "sensor_store_auth",
            scopes = {
                @AuthorizationScope(scope = "write:models", description = "modify sensor model"),
                @AuthorizationScope(scope = "read:models", description = "read sensor model")
            })
    })
@ApiVersion(VersionStatusType.SNAPSHOT)
@CrossOrigin(methods = {RequestMethod.DELETE}, allowedHeaders = {"Siemens-Sensor-Management-Allowed"}, exposedHeaders = {"Siemens-Sensor-Management-Exposed"}, maxAge = 900)
public class SensorControllerImpl extends BaseControllerImpl<SensorEntity, SensorViewEntity, UUID> implements SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/sensors")
    @ResponseBody
    @ApiOperation(
        httpMethod = "POST",
        value = "Finds sensors by IDs",
        notes = "Returns list of sensors",
        nickname = "fetchSensorsByIds",
        tags = {"fetchSensorsByIds"},
        position = 1,
        response = SensorViewEntity.class,
        responseContainer = "Flux",
        consumes = "application/json, application/xml",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        responseHeaders = {
            @ResponseHeader(name = "X-Expires-After", description = "date in UTC when token expires", response = Date.class),
            @ResponseHeader(name = "X-Total-Elements", description = "total number of results in response", response = Integer.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid sensor ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> getSensorData(@ApiParam(value = "Request to fetch sensors by", required = true, readOnly = true) @Valid @RequestBody final SensorDataGetRequest request) {
        log.info("Fetching sensors by IDs: {}", StringUtils.join(request.getItems(), "|"));
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getItemsByIds(ServiceUtils.getItems(request, UUID::fromString)).map(item -> map(item, SensorViewEntity.class))))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), ServiceUtils.getItems(request, UUID::fromString), e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @PostMapping("/sensors/store")
    @ResponseBody
    @ApiOperation(
        httpMethod = "POST",
        value = "Stores sensors data",
        notes = "Save list of sensors data",
        nickname = "storeSensorsDataByIds",
        tags = {"storeSensorsDataByIds"},
        position = 1,
        response = SensorViewEntity.class,
        responseContainer = "Flux",
        consumes = "application/json, application/xml",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        responseHeaders = {
            @ResponseHeader(name = "X-Expires-After", description = "date in UTC when token expires", response = Date.class),
            @ResponseHeader(name = "X-Total-Elements", description = "total number of results in response", response = Integer.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid sensors ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> storeSensorData(@ApiParam(value = "Request to store sensors by", required = true, readOnly = true) @Valid @RequestBody final SensorDataRequest request) {
        log.info("Processing sensor post request: {}", request);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getService().storeSensorData(request)))
            .onErrorResume(e -> Mono.error(ResourceAlreadyExistException.throwResourceExist(getMessageSource(), request.getSensorId(), e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/sensor/{id}")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds sensors by ID",
        notes = "For valid response try correct UUID values, otherwise it will generate API errors",
        nickname = "fetchSensorsById",
        tags = {"fetchSensorsById"},
        position = 2,
        response = SensorViewEntity.class,
        responseContainer = "Mono",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid sensor ID value"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 405, message = "Invalid input value")
    })
    @Override
    public Mono<ServerResponse> getSensorById(@ApiParam(value = "Sensor ID to fetch by", required = true, readOnly = true) @PathVariable("id") final UUID id) {
        log.info("Fetching sensors by ID: {}", id);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getItemById(id).map(item -> map(item, SensorViewEntity.class))))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), id, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/sensors")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds all sensors",
        notes = "Returns list of all sensors",
        nickname = "fetchAllSensors",
        tags = {"fetchAllSensors"},
        position = 3,
        response = SensorViewEntity.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getAllSensors(@ApiParam(value = "Limit to fetch by", readOnly = true) @RequestParam(name = "limit", required = false) final Optional<Integer> limit) {
        log.info("Fetching all sensors by limit: {}", limit);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getAllItems(limit).map(item -> map(item, SensorViewEntity.class))))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/sensor/{id}/metrics")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds all metrics by sensor ID",
        notes = "Returns list of all metrics by sensor ID",
        nickname = "fetchAllMetricsBySensorId",
        tags = {"fetchAllMetricsBySensorId"},
        position = 4,
        response = SensorRecordViewEntity.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getMetricsBySensorId(@ApiParam(value = "Sensor ID to fetch by", required = true, readOnly = true) @PathVariable("id") final UUID sensorId,
                                                     @ApiParam(value = "Page number to filter by") @PageableDefault(size = SensorController.DEFAULT_PAGE_SIZE) @SortDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        log.info("Fetching all metrics by sensor ID: {}, page: {}", sensorId, pageable);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getService().findMetricsById(sensorId, pageable).delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS)).map(item -> map(item, SensorRecordViewEntity.class))))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwMetricsNotFoundBySensorId(getMessageSource(), sensorId, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensors/changes")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds all sensor changes",
        notes = "Returns list of all sensor changes",
        nickname = "fetchSensorChanges",
        tags = {"fetchSensorChanges"},
        position = 6,
        response = Change.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorChanges() {
        log.info("Fetching sensors changes");
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getChanges(SensorEntity.class)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensor/{id}")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds sensor changes by ID",
        notes = "Returns list of sensor changes by ID",
        nickname = "fetchSensorChangesById",
        tags = {"fetchSensorChangesById"},
        position = 7,
        response = Change.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorChangesById(@ApiParam(value = "Sensor id to fetch by", required = true, readOnly = true) @PathVariable("id") final UUID id,
                                                     @ApiParam(value = "Property name to fetch by", readOnly = true) @RequestParam("property") final Optional<String> property) {
        log.info("Fetching sensor changes by id: {}, property: {}", id, property);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getChangesById(id, property, SensorEntity.class)))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), id, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensors/snapshots")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds all sensor snapshots",
        notes = "Returns list of all sensor snapshots",
        nickname = "fetchSensorSnapshots",
        tags = {"fetchSensorSnapshots"},
        position = 8,
        response = CdoSnapshot.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorSnapshots(@ApiParam(value = "Property name to fetch by", readOnly = true) @RequestParam("property") final Optional<String> property) {
        log.info("Fetching sensor snapshots by property: {}", property);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getSnapshots(property)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensor/{id}/snapshots")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds sensor snapshots by id",
        notes = "Returns list of sensor snapshots by ID",
        nickname = "fetchSensorSnapshotsById",
        tags = {"fetchSensorSnapshotsById"},
        position = 9,
        response = CdoSnapshot.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorSnapshotsById(@ApiParam(value = "Sensor id to fetch by", required = true, readOnly = true) @PathVariable("id") final UUID id,
                                                       @ApiParam(value = "Property name to fetch by", readOnly = true) @RequestParam("property") final Optional<String> property) {
        log.info("Fetching sensor snapshots by ID: {}, property: {}", id, property);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getSnapshotsById(id, property, SensorEntity.class)))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourceNotFound(getMessageSource(), id, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensor/{firstId}/diff/{lastId}")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds snapshots between sensors",
        notes = "Returns list of snapshots between sensors",
        nickname = "fetchDiffSensorSnapshots",
        tags = {"fetchDiffSensorSnapshots"},
        position = 10,
        response = Change.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorDiffSnapshots(@ApiParam(value = "First sensor ID to fetch by", required = true, readOnly = true) @PathVariable("firstId") final UUID firstId,
                                                       @ApiParam(value = "Last sensor ID to fetch by", required = true, readOnly = true) @PathVariable("lastId") final UUID lastId) {
        log.info("Fetching sensor snapshots by first ID: {}, last ID: {}", firstId, lastId);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getDiffChanges(firstId, lastId)))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourcesNotFound(getMessageSource(), Arrays.asList(firstId, lastId), e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

    @GetMapping("/audit/sensor/{id}/history")
    @ResponseBody
    @ApiOperation(
        httpMethod = "GET",
        value = "Finds history by sensor ID",
        notes = "Returns list of history items by sensor ID",
        nickname = "fetchSensorHistory",
        tags = {"fetchSensorHistory"},
        position = 11,
        response = Tuple2.class,
        responseContainer = "Flux",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not found")
    })
    @Override
    public Mono<ServerResponse> getSensorHistory(@ApiParam(value = "Sensor ID to fetch by", required = true, readOnly = true) @PathVariable("id") final UUID id) {
        log.info("Fetching sensor history by ID: {}", id);
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(this.getHistory(id, SensorEntity.class)))
            .onErrorResume(e -> Mono.error(ResourceNotFoundException.throwResourceNotFound(getMessageSource(), id, e)))
            .switchIfEmpty(ServerResponse.noContent().build());
    }

//    @GetMapping
//    public Mono<ServerResponse> root() {
//        final Resources<?> resources = new Resources<>(Collections.emptyList());
//        resources.add(linkTo(methodOn(ProductController.class).getAll()).withRel("products"));
//        return ServerResponse.ok().body(BodyInserters.fromObject(Mono.just(resources)));
//    }

//    public Mono<ServerResponse> all(ServerRequest req) {
//        return ServerResponse.ok().body(this.posts.findAll(), Post.class);
//    }
//
//    public Mono<ServerResponse> create(ServerRequest req) {
//        return req.body(BodyExtractors.toMono(Post.class))
//            .flatMap(post -> this.posts.save(post))
//            .flatMap(p -> ServerResponse.created(URI.create("/posts/" + p.getId())).build());
//    }
//
//    public Mono<ServerResponse> get(ServerRequest req) {
//        return this.posts.findById(Long.valueOf(req.pathVariable("id")))
//            .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class))
//            .switchIfEmpty(ServerResponse.notFound().build());
//    }

    @Override
    protected SensorService getService() {
        return this.sensorService;
    }
}
