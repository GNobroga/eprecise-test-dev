package br.com.eprecise.adapter.inbound.exception.handlers;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.EntityNotFoundException;
import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class EntityNotFoundExceptionHandler implements ExceptionMapper<EntityNotFoundException> {

    private static final String ERROR_NAME = "EntityNotFoundError";

    @Override
    public Response toResponse(EntityNotFoundException exception) {
       final ResponseError responseError = ResponseError.builder()
            .title(ERROR_NAME)
            .statusCode(Response.Status.NOT_FOUND.getStatusCode())
            .build();
        return Response.status(Response.Status.NOT_FOUND)
            .entity(responseError.addMessage(exception.getMessage()))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build();
    }

   
}
