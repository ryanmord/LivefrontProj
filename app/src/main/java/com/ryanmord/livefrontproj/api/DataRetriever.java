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

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

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
         * @param data FeedData object with data set according
         *             to success, failure, or error.
         *             <p>
         *             ON SUCCESS data will present and 'articles' variable will contain
         *             structure of items.
         *             <p>
         *             ON FAILURE, FeedData item will be null.
         *             <p>
         *             ON ERROR, 'error' field in FeedData item will be set to true. Errors limited
         *             to network connectivity problems at this time
         */
        void onReceive(Exception exception, List<FeedItem> data);
    }


    /**
     * Application context
     */
    private Context mContext;


    /**
     * Instantiate new DataRetriever
     *
     * @param context   Application Context
     */
    public DataRetriever(Context context) {
        mContext = context;
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
     * Method to perform API call for new feed data.
     *
     * @param callback Callback to notify when call completes.
     */
    public void fetchFeed(final OnFeedDataRetrieved callback) {
        if(!isConnected()) {
            callback.onReceive(new UnknownHostException(), null);
        } else {

            APICalls calls = mRetrofit.create(APICalls.class);
            Call<FeedData> call = calls.getFeedItems();
            call.enqueue(new Callback<FeedData>() {

                @Override
                public void onResponse(Call<FeedData> call, Response<FeedData> response) {
                    callback.onReceive(null, response.body().getArticles());

                }

                @Override
                public void onFailure(Call<FeedData> call, Throwable t) {
                    callback.onReceive(new Exception(t), null);
                }
            });

        }
    }


    /**
     * Gets current network connectivity status
     *
     * @return  True if connected. False otherwise
     */
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)  mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
