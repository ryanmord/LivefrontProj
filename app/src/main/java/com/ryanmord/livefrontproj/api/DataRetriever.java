package com.ryanmord.livefrontproj.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanmord.livefrontproj.api.serializer.DateTimeSerializer;
import com.ryanmord.livefrontproj.objects.FeedData;
import com.ryanmord.livefrontproj.objects.FeedItem;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        void onReceive(boolean error, List<FeedItem> data);
    }


    /**
     * Gson instance used with Retrofit to parse JSON responses into
     * java objects. Configured with custom serializer for handling
     * DateTime objects
     */
    private Gson mGson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
            .create();

    /**
     * Retrofit instance to perform network calls and handle
     * responses
     */
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(Endpoint.ENDPOINT_BASE)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build();

    /**
     * Application context.
     */
    private Context mContext;


    /**
     * Instantiate new DataRetriever
     *
     * @param context   Application context
     */
    public DataRetriever(Context context) {
        mContext = context;
    }


    /**
     * Method to perform API call for new feed data.
     *
     * @param callback  Callback to notify when call completes.
     */
    public void fetchFeed(final OnFeedDataRetrieved callback) {
        //Get connectivity status
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            APICalls calls = mRetrofit.create(APICalls.class);
            Call<FeedData> call = calls.getFeedItems();
            call.enqueue(new Callback<FeedData>() {

                @Override
                public void onResponse(Call<FeedData> call, Response<FeedData> response) {
                    callback.onReceive(false, response.body().getArticles());

                }

                @Override
                public void onFailure(Call<FeedData> call, Throwable t) {
                    callback.onReceive(true, null);
                }
            });
        } else {
            callback.onReceive(true, null);
        }

    }

}
