package br.com.eprecise.adapter.inbound.resources;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
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
    name = "Estados", 
    description = "Permite realizar operações CRUD e consultas sobre a entidade 'Estado', incluindo criação, leitura, atualização e exclusão de registros."
)
@Path("/api/v1/states")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StateResource {

    private final GetAllStateUseCasePort getAllStateUseCasePort;

    private final CreateStateUseCasePort createStateUseCasePort;

    private final UpdateStateUseCasePort updateStateUseCasePort;

    private final DeleteStateUseCasePort deleteStateUseCasePort;

    private final GetStateRecordCountUseCasePort getStateRecordCountUseCasePort;

     
    @Operation(summary = "Permite obter todos as Estados com paginação e filtros.")
    @Parameters(
        value = {
            @Parameter(
                name = "pageSize",
                in = ParameterIn.QUERY,
                description = "Tamanho da página",
                example = "20",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.INTEGER, defaultValue = "20"))
            ),
            @Parameter(
                name = "pageNumber",
                in = ParameterIn.QUERY,
                description = "Número da página",
                example = "1",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.INTEGER, defaultValue = "1"))
            ),
            @Parameter(
                name = "pageOrder",
                in = ParameterIn.QUERY,
                description = "Ordenar por: ASC ou DESC",
                example = "ASC",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.STRING, defaultValue = "ASC"))
            ),
            @Parameter(
                name = "like_filters",
                in = ParameterIn.QUERY,
                description = "Permite filtrar por atributo=valor",
                example = "name=Espírito Santo,abbreviation=ES",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.STRING))
            )
        }
    )
    @GET
    public Page<StateRecordOutput> getAll(@Context UriInfo uriInfo) {
       return getAllStateUseCasePort.execute(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    @Operation(summary = "Permite obter a quantidade de Estados cadastradas.")
    @GET
    @Path("/count")
    public Response getCount() {
        return Response
            .ok(getStateRecordCountUseCasePort.execute())
            .build();
    }

    @Operation(
        summary = "Permite criar um Estado"
    )
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

    @Operation(
        summary = "Permite atualizar um Estado"
    )
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, @Valid UpdateStateRequestDTO request) {
        updateStateUseCasePort.execute(new UpdateStateInput(id, request.getName(), request.getAbbreviation()));
        return Response.status(Response.Status.OK).build();
    }

    @Operation(
        summary = "Permite deletar um Estado"
    )
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        deleteStateUseCasePort.execute(id);
        return Response.ok().build();
    }

}
