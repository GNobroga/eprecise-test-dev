package br.com.eprecise.integration.adapter.inbound.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import br.com.eprecise.adapter.inbound.dtos.CreateCityRequestDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CityResourceTest {

    @Test
    void shouldGetCitiesWhenMakeGetRequest() {
        final Integer expectedPageNumber = 1;
        final Integer expectedPageSize = 20;
        final String expectedPageOrder = "asc";
        final Integer expectedTotalPages = 0;
        final Integer expectTotalItems = 2;
        given()
        .when()
            .get("/api/v1/cities")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("pageNumber", is(expectedPageNumber))
            .body("pageSize", is(expectedPageSize))
            .body("pageOrder", is(expectedPageOrder))
            .body("totalPages", is(expectedTotalPages))
            .body("items", hasSize(expectTotalItems));
    }

    @Test
    void shouldGetCitiesWhenMakeGetRequestWithFilters() {
        final String expectedCityName = "Cruzeiro do Sul";
        given()
            .queryParam("like_filters", String.format("name=%s", expectedCityName))
        .when()
            .get("/api/v1/cities")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("items[0].id", is(notNullValue()))
            .body("items[0].name", is(expectedCityName))
            .body("items[0].population", is(notNullValue()))
            .body("items[0].state", is(notNullValue()));
    }

    @Test
    void shouldCreateANewCityWhenMakePostRequestWithAValidArguments() {
        final CreateCityRequestDTO createCityRequestDTO = new CreateCityRequestDTO("Castelo", "ES", 35000L);
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(createCityRequestDTO)
        .when()
            .post("/api/v1/cities")
        .then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("cityId", is(notNullValue()));
    }

    @Test
    void shouldReturnStatusNotFoundWhenMakePostRequestWithInvalidStateAbbreviation() {
        final CreateCityRequestDTO createCityRequestDTO = new CreateCityRequestDTO("Castelo", "KK", 35000L);
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(createCityRequestDTO)
        .when()
            .post("/api/v1/cities")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void shouldReturnTheCountOfCitiesWhenMakeGetRequestToCountEndpoint() {
        final Integer expectedCityCount = 2;
        given()
        .when()
            .get("/api/v1/cities/count")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("count", is(expectedCityCount));
    }

    @Test
    void shouldReturnCitiesWhenMakeGetRequestByStateId() {
        final String stateId = "123e4567-e89b-12d3-a456-426614174010";
        final Integer expectedSize = 2;
        given()
            .pathParam("id", stateId)
        .when()
            .get("/api/v1/cities/state/{id}")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("items", hasSize(expectedSize));
    }
}
