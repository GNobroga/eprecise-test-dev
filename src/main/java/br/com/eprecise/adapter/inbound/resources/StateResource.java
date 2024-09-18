package br.com.eprecise.adapter.inbound.resources;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import br.com.eprecise.adapter.inbound.utils.ParamUtils;
import br.com.eprecise.application.inbound.state.CreateStateUseCasePort;
import br.com.eprecise.application.inbound.state.DeleteStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetAllStateUseCasePort;
import br.com.eprecise.application.inbound.state.GetStateRecordCountUseCasePort;
import br.com.eprecise.application.inbound.state.UpdateStateUseCasePort;
import br.com.eprecise.application.inbound.state.inputs.CreateStateInput;
import br.com.eprecise.application.inbound.state.inputs.UpdateStateInput;
import br.com.eprecise.application.inbound.state.outputs.StateIdOutput;
import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "States", 
    description = "Permite realizar operações CRUD e consultas sobre a entidade 'State', incluindo criação, leitura, atualização e exclusão de registros."
)
@Path("/v1/states")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class StateResource {

    private final GetAllStateUseCasePort getAllStateUseCasePort;

    private final CreateStateUseCasePort createStateUseCasePort;

    private final UpdateStateUseCasePort updateStateUseCasePort;

    private final DeleteStateUseCasePort deleteStateUseCasePort;

    private final GetStateRecordCountUseCasePort getStateRecordCountUseCasePort;

    @GET
    public Page<StateRecordOutput> getAll(@Context UriInfo uriInfo) {
       return getAllStateUseCasePort.execute(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    @GET
    @Path("/count")
    public Response getCount() {
        return Response
            .ok(getStateRecordCountUseCasePort.execute())
            .build();
    }


    @POST
    public Response create(@Valid CreateStateRequestDTO request, @Context UriInfo uriInfo) {
        final StateIdOutput output = createStateUseCasePort.execute(new CreateStateInput(request.getName(), request.getAbbreviation()));
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").resolveTemplate("id", output.getId()).build();
        return Response
            .status(Response.Status.CREATED)
            .location(uri)
            .entity(output)
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, @Valid UpdateStateRequestDTO request) {
        updateStateUseCasePort.execute(new UpdateStateInput(id, request.getName(), request.getAbbreviation()));
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        deleteStateUseCasePort.execute(id);
        return Response.ok().build();
    }

}
