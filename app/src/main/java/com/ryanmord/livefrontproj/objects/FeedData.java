package com.ryanmord.livefrontproj.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to hold data pertaining to a single API call.
 */
public class FeedData {

    /**
     * Resulting status of call
     */
    @SerializedName("status")
    public String status;

    /**
     * Source of data. ('CNN' in this case)
     */
    @SerializedName("source")
    public String source;

    /**
     * Order by which the data is sorted.
     */
    @SerializedName("sortBy")
    public String sort;

    /**
     * Structure of articles returned from the source
     */
    @SerializedName("articles")
    public List<FeedItem> articles;

    /**
     * Indicates whether an error occurred while trying
     * to perform the API call.
     *
     * True if error occurred. False otherwise
     */
    public boolean error;

}
