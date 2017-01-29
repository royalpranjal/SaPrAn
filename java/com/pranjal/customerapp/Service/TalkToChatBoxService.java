package com.pranjal.customerapp.Service;

import com.pranjal.customerapp.Response.TalkToChatBoxResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by royalpranjal on 1/28/2017.
 */

public interface TalkToChatBoxService {
    @Headers({"X-Mashape-Key: WG3LNYq4J6msh6BJo20B85pz0lkKp1PXYemjsnfQm54sVU8Z5n",
            "Accept: application/json"})
    @GET("/food/converse")
    void getSuggestions(@Query("contextId") String contextId,
                        @Query("text") String text,
                        Callback<TalkToChatBoxResponse> callback);
}
