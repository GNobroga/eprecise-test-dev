package br.com.eprecise.adapter.inbound.resources;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eprecise.adapter.inbound.dtos.CreateStateRequestDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StateResourceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllStatesWithPagination() {
        final Integer expectedPageSize = 5;
        given()
            .queryParam("pageSize",  expectedPageSize)
            .when()
                .get("/api/v1/states")
                .then() 
                    .body("pageSize", is(expectedPageSize))
                    .body("items", hasSize(greaterThan(1)))
                    .body("pageNumber", is(1))
                    .body("pageOrder", is("asc"));
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
    }
}
