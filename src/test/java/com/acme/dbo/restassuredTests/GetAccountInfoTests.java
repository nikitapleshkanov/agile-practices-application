package com.acme.dbo.restassuredTests;

import com.acme.dbo.ClientDto;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

import static com.acme.dbo.ClientEndpoints.BASE_URL;
import static com.acme.dbo.ClientEndpoints.CLIENT_ID;
import static com.acme.dbo.ClientEndpoints.PATH;
import static com.acme.dbo.ClientEndpoints.PORT;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetAccountInfoTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager em;
    private ClientDto client;
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


    @BeforeAll
    public static void setUpDB() {
        entityManagerFactory = Persistence.createEntityManagerFactory("dbo");
    }

    @BeforeEach
    public void setUp() {
        client = new ClientDto()
                .setLogin(clientLogin)
                .setSalt(clientSalt)
                .setSecret(clientSecret)
                .setCreated(LocalDateTime.now())
                .setEnabled(true);
        em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();

    }

    @AfterEach
    public void cleanUp() {
        em.getTransaction().begin();
        final ClientDto savedClient = em.find(ClientDto.class, client.getId());
        em.remove(savedClient);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void checkGettingClientInfo() {
        ClientDto responseClient = RestAssured.given().spec(givenRequest)
                .when()
                .get(CLIENT_ID, client.getId())
                .then()
                .extract()
                .body().as(ClientDto.class);
        checkGetResponse(responseClient);
    }


    private void checkGetResponse(ClientDto newClient) {
        assertEquals(clientLogin, newClient.getLogin());
        assertEquals(client.getId().toString(), String.valueOf(newClient.getId()));
        assertEquals(clientLogin, newClient.getLogin());
        assertEquals(clientSalt, newClient.getSalt());
        assertEquals(clientSecret, newClient.getSecret());
        assertNotNull(newClient.getCreated());
        assertTrue(newClient.getEnabled());
    }


}
