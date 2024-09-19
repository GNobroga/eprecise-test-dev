package br.com.eprecise.adapter.inbound.exception.handlers;

import java.util.Objects;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final String ERROR_NAME = "InternError";

    @Override
    public Response toResponse(Exception exception) {
        final ResponseError responseError = ResponseError.builder()
            .title(ERROR_NAME)
            .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(responseError.addMessage(Objects.nonNull(exception.getMessage()) ? exception.getMessage() : "Unexpected error"))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build();
    }

    
}
