package br.com.eprecise.adapter.inbound.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/")
public class RedirectResource {
    
    @Operation(hidden = true)
    @GET
    public Response redirectToSwagger() {
        return Response.seeOther(URI.create("/swagger-ui")).build();
    }
}
