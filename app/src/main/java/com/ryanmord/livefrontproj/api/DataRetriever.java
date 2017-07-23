package com.ryanmord.livefrontproj.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanmord.livefrontproj.api.serializer.DateTimeSerializer;
import com.ryanmord.livefrontproj.objects.FeedData;
import com.ryanmord.livefrontproj.objects.FeedItem;

import org.joda.time.DateTime;

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

        @GET("/v1/articles?source=cnn&sortBy=top&apiKey=52dabde93cc64788ac49951c32d25d68")
        Call<FeedData> getFeedItems();
    }

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
            .create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    private Context c;


    public DataRetriever(Context context) {
        c = context;
    }




    public boolean fetchFeed(final OnFeedDataRetrieved callback) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            IFeedCalls calls = retrofit.create(IFeedCalls.class);
            Call<FeedData> call = calls.getFeedItems();
            call.enqueue(new Callback<FeedData>() {
                @Override
                public void onResponse(Call<FeedData> call, Response<FeedData> response) {
                    callback.onReceive(response.body());

                }

                @Override
                public void onFailure(Call<FeedData> call, Throwable t) {
                    callback.onReceive(null);
                }
            });
        }

        return isConnected;
    }

}
