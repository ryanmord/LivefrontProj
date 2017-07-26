package com.ryanmord.livefrontproj.api;

import com.ryanmord.livefrontproj.objects.FeedData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface used in conjunction with Retrofit to supply endpoints
 * for API calls
 */
interface APICalls {

    /**
     * Endpoint for CNN API interaction.
     *
     * @return  Call object to use in API call
     */
    @GET(Endpoint.ENDPOINT_CNN)
    Call<FeedData> getFeedItems();

}
