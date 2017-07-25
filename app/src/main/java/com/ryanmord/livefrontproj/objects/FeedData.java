package com.ryanmord.livefrontproj.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ryanmord on 7/20/17.
 */

public class FeedData {

    @SerializedName("status")
    public String status;

    @SerializedName("source")
    public String source;

    @SerializedName("sortBy")
    public String sort;

    @SerializedName("articles")
    public List<FeedItem> articles;

    public boolean error;

}
