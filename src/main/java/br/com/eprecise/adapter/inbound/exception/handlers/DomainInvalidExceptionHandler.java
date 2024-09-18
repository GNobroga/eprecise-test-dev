package br.com.eprecise.adapter.inbound.exception.handlers;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eprecise.domain.exceptions.DomainInvalidException;
import br.com.eprecise.domain.exceptions.ResponseError;

@Provider
public class DomainInvalidExceptionHandler implements ExceptionMapper<DomainInvalidException> {

    private static final String ERROR_NAME = "DomainInvalidError";

    @Override
    public Response toResponse(DomainInvalidException exception) {
         final ResponseError responseError = ResponseError.builder()
            .title(ERROR_NAME)
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .build();

        exception.getErrors().forEach(message -> responseError.addMessage(message.getMessage()));
        
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(responseError)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build();
    }
    
}
