package br.com.eprecise.adapter.inbound.exception.handlers;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class ValidationExceptionExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final String ERROR_NAME = "ValidationError";

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final ResponseError responseError = ResponseError.builder().title(ERROR_NAME)
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .build();

        exception.getConstraintViolations().forEach(constraint -> responseError.addMessage(constraint.getMessage()));
        
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(responseError)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build();
    }

 
    
}
