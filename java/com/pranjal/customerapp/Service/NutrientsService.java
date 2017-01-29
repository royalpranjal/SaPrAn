package com.pranjal.customerapp.Service;

import com.pranjal.customerapp.Response.NutrientsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by royalpranjal on 1/29/2017.
 */

public interface NutrientsService {
    @Headers({"X-Mashape-Key: WG3LNYq4J6msh6BJo20B85pz0lkKp1PXYemjsnfQm54sVU8Z5n",
            "Accept: application/json"})
    @GET("/v1_1/search/{phrase}")
    void getNutrients(
        @Path("phrase") String phrase,
        @Query("fields") String fields,
        Callback<NutrientsResponse> callback);
}
