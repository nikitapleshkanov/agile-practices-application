package com.acme.dbo.restassuredTests;

import com.acme.dbo.ClientDto;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.acme.dbo.AccountEndpoints.BASE_URL;
import static com.acme.dbo.AccountEndpoints.CLIENT;
import static com.acme.dbo.AccountEndpoints.CLIENT_ID;
import static com.acme.dbo.AccountEndpoints.PATH;
import static com.acme.dbo.AccountEndpoints.PORT;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetAccountInfoTests {

    private RequestSpecification givenRequest = given()
            .baseUri(BASE_URL)
            .port(PORT)
            .basePath(PATH)
            .header("X-API-VERSION", 1)
            .contentType("application/json")
            .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    private Response response;
    private String clientLogin = "adminNewProfile5@email.com";
    private String clientSalt = "some-salt";
    private String clientSecret = "749f09bade8aca7556749f09bade8aca7556";

    @BeforeEach
    public void setUp() {
        response = RestAssured.given().spec(givenRequest)
                .body(new ClientDto()
                        .setLogin(clientLogin)
                        .setSalt(clientSalt)
                        .setSecret(clientSecret))
                .post(CLIENT);
    }

    @AfterEach
    public void cleanUp() {
        RestAssured.given().spec(givenRequest)
                .delete(CLIENT_ID, response.path("id").toString());
    }

    @Test
    public void checkGettingClientInfo() {
        ClientDto client = RestAssured.given().spec(givenRequest)
                .when()
                .get(CLIENT_ID, response.path("id").toString())
                .then()
                .extract()
                .body().as(ClientDto.class);
        checkGetResponse(client);
    }


    private void checkGetResponse(ClientDto client) {
        assertEquals(clientLogin, client.getLogin());
        assertEquals(response.path("id").toString(), String.valueOf(client.getId()));
        assertEquals(clientLogin, client.getLogin());
        assertEquals(clientSalt, client.getSalt());
        assertEquals(clientSecret, client.getSecret());
        assertNotNull(client.getCreated());
        assertTrue(client.getEnabled());
    }


}
