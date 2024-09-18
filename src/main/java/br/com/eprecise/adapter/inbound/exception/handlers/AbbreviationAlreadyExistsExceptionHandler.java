package br.com.eprecise.adapter.inbound.exception.handlers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.AbbreviationAlreadyExistsException;
import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class AbbreviationAlreadyExistsExceptionHandler implements ExceptionMapper<AbbreviationAlreadyExistsException> {

    @Override
    public Response toResponse(AbbreviationAlreadyExistsException exception) {
       final ResponseError responseError = ResponseError.builder()
            .title("AbbreviationAlreadyExistsError")
            .statusCode(Response.Status.CONFLICT.getStatusCode())
            .build();

        return Response
            .status(Response.Status.CONFLICT)
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .entity(responseError.addMessage(exception.getMessage()))
            .build();
    }
   
}
