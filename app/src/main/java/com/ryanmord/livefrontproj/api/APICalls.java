package com.ryanmord.livefrontproj.api;

import com.ryanmord.livefrontproj.objects.FeedData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface used in conjuntion with Retrofit to supply endpoints
 * for API calls
 */
interface APICalls {

    /**
     * Endoint for CNN API interaction
     *
     * @return  Call object to use in API call
     */
    @GET("/v1/articles?source=cnn&sortBy=top&apiKey=52dabde93cc64788ac49951c32d25d68")
    Call<FeedData> getFeedItems();

}
