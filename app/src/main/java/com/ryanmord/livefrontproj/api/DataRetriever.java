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
 * Class used for API calls, data retrieval, and Json parsing.
 */
public class DataRetriever {

    /**
     * Interface for notifying when network call has completed
     */
    public interface OnFeedDataRetrieved {

        /**
         * Called when an API call finishes.
         *
         * @param data  FeedData object with data set according
         *              to success, failure, or error.
         *
         *              ON SUCCESS data will present and 'articles' variable will contain
         *              structure of items.
         *
         *              ON FAILURE, FeedData item will be null.
         *
         *              ON ERROR, 'error' field in FeedData item will be set to true. Errors limited
         *              to network connectivity problems at this time
         */
        void onReceive(FeedData data);
    }


    /**
     * Gson instance used with Retrofit to parse JSON responses into
     * java objects. Configured with custom serializer for handling
     * DateTime objects
     */
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
            .create();

    /**
     * Retrofit instance to perform network calls and handle
     * responses
     */
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    /**
     * Application context.
     */
    private Context c;


    /**
     * Instantiate new DataRetriever
     *
     * @param context   Application context
     */
    public DataRetriever(Context context) {
        c = context;
    }


    /**
     * Method to perform API call for new feed data.
     *
     * @param callback  Callback to notify when call completes.
     */
    public void fetchFeed(final OnFeedDataRetrieved callback) {
        //Get connectivity status
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            APICalls calls = retrofit.create(APICalls.class);
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
        } else {
            //instantiate new FeedData with error set to false
            //for fragment handling.
            FeedData errorData = new FeedData();
            errorData.error = true;
            callback.onReceive(errorData);
        }

    }

}
