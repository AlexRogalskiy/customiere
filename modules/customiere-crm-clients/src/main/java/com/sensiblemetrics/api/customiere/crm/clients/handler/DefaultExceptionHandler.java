package com.sensiblemetrics.api.customiere.crm.clients.handler;

import com.sensiblemetrics.api.customiere.commons.exception.*;
import com.sensiblemetrics.api.customiere.crm.clients.model.dto.ExceptionViewEntity;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseBody
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<?> handleResourceAlreadyExistException(final HttpServletRequest req,
                                                                    final ResourceAlreadyExistException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> handleResourceNotFoundException(final HttpServletRequest req,
                                                                final ResourceNotFoundException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleBadRequestException(final HttpServletRequest req,
                                                          final BadRequestException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmptyContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity<?> handleEmptyContentException(final HttpServletRequest req,
                                                            final EmptyContentException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleConstraintViolationException(final HttpServletRequest req,
                                                                   final ConstraintViolationException ex) {
        final String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return this.buildResponse(
            req.getServletPath(),
            message,
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleInvalidFormatException(final HttpServletRequest req,
                                                             final InvalidFormatException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> handleServiceException(final HttpServletRequest req,
                                                       final ServiceException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied")
    protected ResponseEntity<?> handleAccessDeniedException(final HttpServletRequest req,
                                                            final AccessDeniedException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad argument/state")
    protected ResponseEntity<?> handleIllegalArgumentException(final HttpServletRequest req,
                                                               final RuntimeException ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> handleAllException(final HttpServletRequest req,
                                                   final Exception ex) {
        return this.buildResponse(
            req.getServletPath(),
            ex.getLocalizedMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    protected ResponseEntity<?> buildResponse(final String path,
                                              final String message,
                                              final HttpStatus status) {
        return ResponseEntity.status(status).body(ExceptionViewEntity.of(path, message, status));
    }
}
