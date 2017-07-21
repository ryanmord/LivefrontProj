package com.ryanmord.livefrontproj.api;

import android.util.Log;

import com.google.gson.Gson;
import com.ryanmord.livefrontproj.objects.FeedData;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ryanmord on 7/20/17.
 */

public class DataRetriever {

    public interface OnFeedDataRetrieved {
        void onReceive(FeedData data);
    }

    private interface IFeedCalls {
        @GET("/v1/articles?source=techcrunch&sortBy=latest&apiKey=52dabde93cc64788ac49951c32d25d68")
        Call<FeedData> getFeedItems();
    }

    private Gson g;
    private String key = "52dabde93cc64788ac49951c32d25d68";
    private String extra = "/v1/articles?source=techcrunch&sortBy=latest&apiKey=52dabde93cc64788ac49951c32d25d68";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();


    public void fetchFeed(OnFeedDataRetrieved callback) {

        IFeedCalls calls = retrofit.create(IFeedCalls.class);
        Call<FeedData> call = calls.getFeedItems();
        call.enqueue(new Callback<FeedData>() {
            @Override
            public void onResponse(Call<FeedData> call, Response<FeedData> response) {
                Log.d("PAYLOAD", response.body().toString());
            }

            @Override
            public void onFailure(Call<FeedData> call, Throwable t) {
                Log.d("PAYLOAD", "FAILED");
            }
        });
    }

}
