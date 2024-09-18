package br.com.eprecise.adapter.inbound.exception.handlers;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.AbbreviationAlreadyExistsException;
import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class AbbreviationAlreadyExistsExceptionHandler implements ExceptionMapper<AbbreviationAlreadyExistsException> {

    private static final String ERROR_NAME = "AbbreviationAlreadyExistsError";

    @Override
    public Response toResponse(AbbreviationAlreadyExistsException exception) {
       final ResponseError responseError = ResponseError.builder()
            .title(ERROR_NAME)
            .statusCode(Response.Status.CONFLICT.getStatusCode())
            .build();

        return Response
            .status(Response.Status.CONFLICT)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .entity(responseError.addMessage(exception.getMessage()))
            .build();
    }
   
}
