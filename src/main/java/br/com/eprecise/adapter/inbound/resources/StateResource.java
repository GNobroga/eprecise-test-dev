package br.com.eprecise.adapter.inbound.resources;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.eprecise.adapter.inbound.dtos.CreateStateRequestDTO;
import br.com.eprecise.adapter.inbound.dtos.UpdateStateRequestDTO;
import br.com.eprecise.adapter.inbound.facade.StateFacade;
import br.com.eprecise.adapter.inbound.resources.swagger.StateResourceAPI;
import br.com.eprecise.adapter.inbound.utils.ParamUtils;
import br.com.eprecise.application.inbound.state.inputs.CreateStateInput;
import br.com.eprecise.application.inbound.state.inputs.UpdateStateInput;
import br.com.eprecise.application.inbound.state.outputs.StateIdOutput;
import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Estados", 
    description = "Permite realizar operações CRUD e consultas sobre a entidade 'Estado', incluindo criação, leitura, atualização e exclusão de registros."
)
@Path("/api/v1/states")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StateResource implements StateResourceAPI {

    private final StateFacade stateFacade;

    public Page<StateRecordOutput> getAll(@Context UriInfo uriInfo) {
       return stateFacade.findAll(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    public Response create(@Valid CreateStateRequestDTO request, @Context UriInfo uriInfo) {
        final StateIdOutput output = stateFacade.create(new CreateStateInput(request.getName(), request.getAbbreviation()));
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").resolveTemplate("id", output.getStateId()).build();
        return Response
            .status(Response.Status.CREATED)
            .location(uri)
            .entity(output)
            .build();
    }

    public Response update(@PathParam("id") String id, @Valid UpdateStateRequestDTO request) {
        stateFacade.update(new UpdateStateInput(id, request.getName(), request.getAbbreviation()));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Response delete(@PathParam("id") String id) {
        return Response.ok().entity(stateFacade.deleteById(id)).build();
    }

    public Response getCount() {
        return Response
            .ok(stateFacade.count())
            .build();
    }

}
