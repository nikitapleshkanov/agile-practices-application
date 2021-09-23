package com.acme.dbo.restassuredTests;

import com.acme.dbo.ClientDto;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.acme.dbo.AccountEndpoints.BASE_URL;
import static com.acme.dbo.AccountEndpoints.CLIENT;
import static com.acme.dbo.AccountEndpoints.CLIENT_ID;
import static com.acme.dbo.AccountEndpoints.PATH;
import static com.acme.dbo.AccountEndpoints.PORT;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class DeleteAccountTests {

    private RequestSpecification givenRequest = given()
            .baseUri(BASE_URL)
            .port(PORT)
            .basePath(PATH)
            .header("X-API-VERSION", 1)
            .contentType("application/json")
            .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    private Response response;
    private String clientLogin = "adminNewProfile1@email.com";
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

    @Test
    public void checkDeleteClientInfo() {
        RestAssured.given().spec(givenRequest)
                .when()
                .delete(CLIENT_ID, response.path("id").toString())
                .then()
                .statusCode(is(SC_OK));

    }

}
