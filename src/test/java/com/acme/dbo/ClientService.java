package com.acme.dbo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ClientService {

    @GET("client")
    @Headers("X-API-VERSION:1")
    Call<List<ClientDto>> getClients();

    @POST("client")
    @Headers("X-API-VERSION:1")
    Call<ClientDto> createClient(@Body ClientDto clientDto);

    @DELETE("client/{id}")
    @Headers("X-API-VERSION:1")
    Call<Void> deleteClient(@Path("id") Integer id);

}
