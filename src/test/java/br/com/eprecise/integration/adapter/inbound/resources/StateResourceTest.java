package br.com.eprecise.integration.adapter.inbound.resources;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eprecise.adapter.inbound.dtos.CreateStateRequestDTO;
import br.com.eprecise.adapter.inbound.dtos.UpdateStateRequestDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StateResourceTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetAllStatesWithPagination() {
        final Integer expectedPageSize = 5;  
        final Integer expectedPageNumber = 1; 
        final String expectedPageOrder = "asc";  
        given()
            .queryParam("pageSize",  expectedPageSize)
            .when()
                .get("/api/v1/states")
                .then() 
                    .body("pageSize", is(expectedPageSize))
                    .body("items", hasSize(greaterThan(1)))
                    .body("pageNumber", is(expectedPageNumber))
                    .body("pageOrder", is(expectedPageOrder));
    }

    @Test
    void shouldCreateStateSuccessfullyWhenValidInputIsProvided() throws JsonProcessingException {
        final CreateStateRequestDTO createStateRequestDTO = new CreateStateRequestDTO("VINDO TESTE", "VT");
        given()
            .body(objectMapper.writeValueAsString(createStateRequestDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .post("/api/v1/states")
            .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("stateId", notNullValue());

            given() 
                .queryParam("like_filters", String.format("name=%s", createStateRequestDTO.getName()))
                .when()
                    .get("/api/v1/states")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body("items[0].id", is(notNullValue()))
                    .body("items[0].name", is(createStateRequestDTO.getName()))
                    .body("items[0].abbreviation", is(createStateRequestDTO.getAbbreviation()));
    }

    @Test
    void shouldThrowExceptionWhenInvalidInputIsProvided() throws JsonProcessingException {
        final CreateStateRequestDTO createStateRequestDTO = new CreateStateRequestDTO(null, null);
        given()
            .body(objectMapper.writeValueAsString(createStateRequestDTO))
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .post("/api/v1/states")
            .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .body("messages[0]", is(notNullValue()))
                .body("messages[1]", is(notNullValue()));
    }

    @Test
    void shouldUpdateStateWhenMakePutRequest() throws JsonProcessingException {
        final UpdateStateRequestDTO updateStateRequestDTO = new UpdateStateRequestDTO("Avenida Brasil 2", "AB");
        final String targetId = "123e4567-e89b-12d3-a456-426614174010";
        given() 
            .body(objectMapper.writeValueAsString(updateStateRequestDTO))
            .pathParam("id", targetId)
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .put("/api/v1/states/{id}")
            .then() 
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given() 
            .queryParam("like_filters", String.format("id=%s", targetId))
            .when()
                .get("/api/v1/states")
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("items[0].id", is(targetId))
                .body("items[0].name", is(updateStateRequestDTO.getName()))
                .body("items[0].abbreviation", is(updateStateRequestDTO.getAbbreviation()));
    } 

    @Test
    void shouldReturnErrorMessagesWhenPutRequestContainsInvalidArguments() throws JsonProcessingException {
        final UpdateStateRequestDTO updateStateRequestDTO = new UpdateStateRequestDTO("Avenida Brasil", "AB");
        final String targetId = "123";
        given() 
            .body(objectMapper.writeValueAsString(updateStateRequestDTO))
            .pathParam("id", targetId)
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .put("/api/v1/states/{id}")
            .then() 
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body("messages[0]", is(String.format("Entity %s with identifier %s not found.", "State", targetId)));
    }

    @Test
    void shouldReturnStateCountWhenGetRequestIsMadeToCountEndpoint() {
        final Integer expectedCount = 28;
        given() 
        .when()
        .get("/api/v1/states/count")
        .then() 
            .statusCode(Response.Status.OK.getStatusCode())
            .body("count", is(expectedCount));
    }
}
