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

import br.com.eprecise.adapter.inbound.dtos.CreateCityRequestDTO;
import br.com.eprecise.adapter.inbound.dtos.UpdateCityRequestDTO;
import br.com.eprecise.adapter.inbound.facade.CityFacade;
import br.com.eprecise.adapter.inbound.resources.swagger.CityResourceAPI;
import br.com.eprecise.adapter.inbound.utils.ParamUtils;
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
public class CityResource implements CityResourceAPI {
    
    private final CityFacade cityFacade;
    
    public Page<CityRecordOutput> getAll(@Context UriInfo uriInfo) {
        return cityFacade.findAll(new SearchCriteria(ParamUtils.getParams(uriInfo.getQueryParameters())));
    }

    public Page<CityRecordOutput> searchByName(@PathParam("name") String name, @Context UriInfo uriInfo) {
        return cityFacade.findByName(new CitySearchByNameInput(name, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
     } 


    public Response getCount() {
        return Response
            .ok(cityFacade.count())
            .build();
    }

    public Page<CityRecordOutput> getByState(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return cityFacade.findByStateId(new CitySearchByStateInput(id, Pagination.create(ParamUtils.getParams(uriInfo.getQueryParameters()))));
    }

    public Response create(@Valid CreateCityRequestDTO request, @Context UriInfo uriInfo) {
          final CityIdOutput output = cityFacade.create(new CreateCityInput(request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        final URI uri = uriInfo.getAbsolutePathBuilder().
            queryParam(SearchCriteria.FILTER_KEY, String.format("id=%s", output.getCityId()))
            .build();
        return Response
            .status(Response.Status.CREATED)
            .location(uri)
            .entity(output)
            .build();
    }

   
    public Response update(@PathParam("id") String id, @Valid UpdateCityRequestDTO request) {
        cityFacade.update(new UpdateCityInput(id, request.getName(), request.getStateAbbreviation(), request.getPopulation()));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Response delete(@PathParam("id") String id) {
        return Response.ok().entity(cityFacade.deleteById(id)).build();
    }
}
