package com.pranjal.customerapp.Service;

import com.pranjal.customerapp.Request.ImageReq;
import com.pranjal.customerapp.Response.ImageResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by royalpranjal on 1/29/2017.
 */

public interface ImageRequest {
    @Headers({"Content-Type: application/json",
            "Ocp-Apim-Subscription-Key: 8855126795f34af5a4bd36172faa4ac6"})
    @POST("/vision/v1.0/tag")
    void postURL(@Body ImageReq req, Callback<ImageResponse> callback);
}
