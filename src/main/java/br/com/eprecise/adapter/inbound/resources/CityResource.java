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

import br.com.eprecise.adapter.inbound.dtos.CreateCityRequestDTO;
import br.com.eprecise.adapter.inbound.dtos.UpdateCityRequestDTO;
import br.com.eprecise.adapter.inbound.utils.ParamUtils;
import br.com.eprecise.application.inbound.city.CreateCityUseCasePort;
import br.com.eprecise.application.inbound.city.DeleteCityUseCasePort;
import br.com.eprecise.application.inbound.city.FindCityByNameUseCasePort;
import br.com.eprecise.application.inbound.city.GetAllCityUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityByStateIdUseCasePort;
import br.com.eprecise.application.inbound.city.GetCityRecordCountUseCasePort;
import br.com.eprecise.application.inbound.city.UpdateCityUseCasePort;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByNameInput;
import br.com.eprecise.application.inbound.city.inputs.CitySearchByStateInput;
import br.com.eprecise.application.inbound.city.inputs.CreateCityInput;
import br.com.eprecise.application.inbound.city.inputs.UpdateCityInput;
import br.com.eprecise.application.inbound.city.outputs.CityIdOutput;
import br.com.eprecise.application.inbound.city.outputs.CityRecordOutput;
import br.com.eprecise.domain.filter.SearchCriteria;
import br.com.eprecise.domain.pagination.Page;
import br.com.eprecise.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Cidades", 
    description = "Permite realizar operações CRUD e consultas sobre a entidade 'Cidade', incluindo criação, leitura, atualização e exclusão de registros."
)
@Path("/api/v1/cities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CityResource {
    
    private final CreateCityUseCasePort createCityUseCasePort;

    private final DeleteCityUseCasePort deleteCityUseCasePort;

    private final GetAllCityUseCasePort getAllCityUseCasePort;

    private final UpdateCityUseCasePort updateCityUseCasePort;

    private final GetCityRecordCountUseCasePort getCityRecordCountUseCasePort;

    private final GetCityByStateIdUseCasePort getCityByStateIdUseCasePort;

    private final FindCityByNameUseCasePort findCityByNameUseCasePort;
    
    @Operation(summary = "Permite obter todas as Cidades com paginação e filtros.")
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
                example = "name=Rio Branco,state.name=Acre",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.STRING))
            )
        }
    )
    @GET
    public Page<CityRecordOutput> getAll(@Context UriInfo uriInfo) {
        return getAllCityUseCasePort.execute(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    @Operation(summary = "Permite pesquisar Cidades pelo nome com paginação.")
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
                name = "name",
                in = ParameterIn.PATH,
                description = "Permite pesquisar a cidade pelo nome, mínimo 3 caracteres.",
                example = "Castelo",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.STRING))
            ),
        }
    )
    @GET
    @Path("/search-by-name/{name}")
    public Page<CityRecordOutput> searchByName(@PathParam("name") String name, @Context UriInfo uriInfo) {
        return findCityByNameUseCasePort.execute(new CitySearchByNameInput(name, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
     } 

    @Operation(summary = "Permite obter a quantidade de Cidades cadastradas.")
    @GET
    @Path("/count")
    public Response getCount() {
        return Response
            .ok(getCityRecordCountUseCasePort.execute())
            .build();
    }

    @Operation(summary = "Permite obter as Cidades de um Estado.")
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
                name = "id",
                in = ParameterIn.PATH,
                description = "Permite obter cidades de um determinado Estado pelo id dele",
                example = "123e4567-e89b-12d3-a456-426614174010",
                required = false,
                content = @Content(schema = @Schema(type = SchemaType.STRING))
            )
        }
    )
    @GET
    @Path("/state/{id}")
    public Page<CityRecordOutput> getByState(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return getCityByStateIdUseCasePort.execute(new CitySearchByStateInput(id, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
    }

    @Operation(
        summary = "Permite criar uma Cidade"
    )
    @POST
    public Response create(@Valid CreateCityRequestDTO request, @Context UriInfo uriInfo) {
          final CityIdOutput output = createCityUseCasePort.execute(new CreateCityInput(request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        final URI uri = uriInfo.getAbsolutePathBuilder().
            queryParam("like_filters", String.format("id=%s", output.getCityId()))
            .build();
        return Response
            .status(Response.Status.CREATED)
            .location(uri)
            .entity(output)
            .build();
    }

    @Operation(
        summary = "Permite atualizar uma Cidade"
    )
    @Parameter(
        name = "id",
        in = ParameterIn.PATH,
        description = "Id da cidade a ser atualizada",
        example = "123e4567-e89b-12d3-a456-426614174010",
        required = false,
        content = @Content(schema = @Schema(type = SchemaType.STRING))
    )
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, @Valid UpdateCityRequestDTO request) {
        updateCityUseCasePort.execute(new UpdateCityInput(id, request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        return Response.status(Response.Status.OK).build();
    }

    @Operation(
        summary = "Permite deletar uma cidade"
    )
    @Parameter(
        name = "id",
        in = ParameterIn.PATH,
        description = "Id da cidade a ser deletada",
        example = "123e4567-e89b-12d3-a456-426614174010",
        required = false,
        content = @Content(schema = @Schema(type = SchemaType.STRING))
    )
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        deleteCityUseCasePort.execute(id);
        return Response.ok().build();
    }
}
