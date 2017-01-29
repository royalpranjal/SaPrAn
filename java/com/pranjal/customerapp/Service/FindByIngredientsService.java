package com.pranjal.customerapp.Service;

import com.pranjal.customerapp.Response.FindByIngredientsResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by royalpranjal on 1/28/2017.
 */

public interface FindByIngredientsService {
    @Headers({"X-Mashape-Key: WG3LNYq4J6msh6BJo20B85pz0lkKp1PXYemjsnfQm54sVU8Z5n",
            "Accept: application/json"})
    @GET("/recipes/findByIngredients")
    void getRecipeList(
            @Query("fillIngredients") boolean fillIngredients,
            @Query("ingredients") String ingredients,
            @Query("limitLicense") boolean limitLicense,
            @Query("number") int number,
            @Query("ranking") int ranking,
            Callback<List<FindByIngredientsResponse>> response
    );
}
