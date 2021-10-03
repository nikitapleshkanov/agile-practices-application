package com.acme.dbo.restassuredTests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

import static com.acme.dbo.ClientEndpoints.BASE_URL;
import static com.acme.dbo.ClientEndpoints.CLIENT_ID;
import static com.acme.dbo.ClientEndpoints.PATH;
import static com.acme.dbo.ClientEndpoints.PORT;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class DeleteAccountTests {

    private static Connection connection;
    int clientId;
    private RequestSpecification givenRequest = given()
            .baseUri(BASE_URL)
            .port(PORT)
            .basePath(PATH)
            .header("X-API-VERSION", 1)
            .contentType("application/json")
            .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    private String clientLogin = "adminNewProfile1@email.com";
    private String clientSalt = "some-salt";
    private String clientSecret = "749f09bade8aca7556749f09bade8aca7556";


    @BeforeAll
    public static void setUpDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:derby://localhost/dbo-db");
    }

    @AfterAll
    public static void cleanUpDB() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void setUp() throws SQLException {

        try (final PreparedStatement newClient = connection.prepareStatement("INSERT INTO CLIENT(LOGIN, SECRET, SALT, CREATED, ENABLED) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            newClient.setString(1, clientLogin);
            newClient.setString(2, clientSecret);
            newClient.setString(3, clientSalt);
            newClient.setTimestamp(4, Timestamp.from(Instant.now()));
            newClient.setBoolean(5, true);
            Assertions.assertEquals(newClient.executeUpdate(), 1);

            try (final ResultSet generatedKeys = newClient.getGeneratedKeys()) {
                Assertions.assertTrue(generatedKeys.next());
                clientId = generatedKeys.getInt(1);
            }

        }

    }

    @Test
    public void checkDeleteClientInfo() {
        RestAssured.given().spec(givenRequest)
                .when()
                .delete(CLIENT_ID, clientId)
                .then()
                .statusCode(is(SC_OK));

    }

}
