package com.acme.dbo.retrofitTests;

import com.acme.dbo.ClientDto;
import com.acme.dbo.ClientService;
import lombok.SneakyThrows;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetClientsTests {

    ClientDto client;
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(BASE_URL + ':' + PORT + PATH)
            .build();
    ClientService service = retrofit.create(ClientService.class);;
    private String clientLogin = "adminTest123@email.com";
    private String clientSalt = "some-salt";
    private String clientSecret = "749f09bade8aca7556749f09bade8aca7556";

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        client = service.createClient(new ClientDto()
                .setLogin(clientLogin)
                .setSalt(clientSalt)
                .setSecret(clientSecret)).execute().body();
    }

    @AfterEach
    public void cleanUp() throws IOException {
        service.deleteClient(client.getId()).execute();
    }

    @Test
    public void checkGettingClientsInfo() throws IOException {
        List<ClientDto> clients = service.getClients().execute().body();
        assertTrue(clients.stream()
                .anyMatch(clientDto -> String.valueOf(clientDto.getId()).equals(String.valueOf(client.getId()))));

    }

}
