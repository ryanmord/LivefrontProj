package com.ryanmord.livefrontproj.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to hold data pertaining to a single API call.
 */
public class FeedData {

    /**
     * Data keys used to parse data values from JSON response.
     */
    private final String  KEY_STATUS     = "status";
    private final String  KEY_SOURCE     = "source";
    private final String  KEY_SORT       = "sortBy";
    private final String  KEY_ARTICLES   = "articles";


    /**
     * Resulting status of call
     */
    @SerializedName(KEY_STATUS)
    private String mStatus;

    /**
     * Source of data. ('CNN' in this case)
     */
    @SerializedName(KEY_SOURCE)
    private String mSource;

    /**
     * Order by which the data is sorted.
     */
    @SerializedName(KEY_SORT)
    private String mSort;

    /**
     * Structure of articles returned from the source
     */
    @SerializedName(KEY_ARTICLES)
    private List<FeedItem> mArticles;

    /**
     * Indicates whether an error occurred while trying
     * to perform the API call.
     *
     * True if error occurred. False otherwise
     */
    public boolean error;


    /**
     * @return  Structure of article data
     */
    public List<FeedItem> getArticles() {
        return mArticles;
    }
}
