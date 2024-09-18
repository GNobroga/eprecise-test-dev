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

@Path("/v1/cities")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CityResource {
    
    private final CreateCityUseCasePort createCityUseCasePort;

    private final DeleteCityUseCasePort deleteCityUseCasePort;

    private final GetAllCityUseCasePort getAllCityUseCasePort;

    private final UpdateCityUseCasePort updateCityUseCasePort;

    private final GetCityRecordCountUseCasePort getCityRecordCountUseCasePort;

    private final GetCityByStateIdUseCasePort getCityByStateIdUseCasePort;

    private final FindCityByNameUseCasePort findCityByNameUseCasePort;

    @GET
    public Page<CityRecordOutput> getAll(@Context UriInfo uriInfo) {
        return getAllCityUseCasePort.execute(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    @GET
    @Path("/search-by-name/{name}")
    public Page<CityRecordOutput> searchByName(@PathParam("name") String name, @Context UriInfo uriInfo) {
        return findCityByNameUseCasePort.execute(new CitySearchByNameInput(name, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
     } 

    @GET
    @Path("/count")
    public Response getCount() {
        return Response
            .ok(getCityRecordCountUseCasePort.execute())
            .build();
    }

    @GET
    @Path("/state/{id}")
    public Page<CityRecordOutput> getByState(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return getCityByStateIdUseCasePort.execute(new CitySearchByStateInput(id, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
    }

    @POST
    public Response create(CreateCityRequestDTO request, @Context UriInfo uriInfo) {
          final CityIdOutput output = createCityUseCasePort.execute(new CreateCityInput(request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").resolveTemplate("id", output.getCityId()).build();
        return Response
            .status(Response.Status.CREATED)
            .location(uri)
            .entity(output)
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, @Valid UpdateCityRequestDTO request) {
        updateCityUseCasePort.execute(new UpdateCityInput(id, request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        deleteCityUseCasePort.execute(id);
        return Response.ok().build();
    }
}
