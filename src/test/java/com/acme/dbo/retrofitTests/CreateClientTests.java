package com.acme.dbo.retrofitTests;

import com.acme.dbo.ClientDto;
import com.acme.dbo.ClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

import static com.acme.dbo.AccountEndpoints.BASE_URL;
import static com.acme.dbo.AccountEndpoints.PATH;
import static com.acme.dbo.AccountEndpoints.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateClientTests {

    ClientService service;
    ClientDto client;
    Retrofit retrofit;
    private String clientLogin = "adminTest1234@email.com";
    private String clientSalt = "some-salt";
    private String clientSecret = "749f09bade8aca7556749f09bade8aca7556";

    @BeforeEach
    public void setUp() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL + ':' + PORT + PATH)
                .build();
        service = retrofit.create(ClientService.class);
    }

    @AfterEach
    public void cleanUp() throws IOException {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL + ':' + PORT + PATH)
                .build();
        service = retrofit.create(ClientService.class);
        service.deleteClient(client.getId()).execute();
    }

    @Test
    public void checkCreatingClient() throws IOException {
        client = service.createClient(new ClientDto()
                .setLogin(clientLogin)
                .setSalt(clientSalt)
                .setSecret(clientSecret)).execute().body();
        checkClientCreation(client);

    }

    public void checkClientCreation(ClientDto client) throws IOException {
        List<ClientDto> clients = service.getClients().execute().body();
        assertTrue(clients.stream()
                .anyMatch(clientDto -> String.valueOf(clientDto.getId()).equals(String.valueOf(client.getId()))));
        assertNotNull(client.getId());
        assertEquals(clientLogin, client.getLogin());
        assertEquals(clientSecret, client.getSecret());
        assertEquals(clientSalt, client.getSalt());
    }

}
