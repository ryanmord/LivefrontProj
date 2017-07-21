package com.ryanmord.livefrontproj.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryanmord on 7/20/17.
 */

public class FeedItem {

    @SerializedName("author")
    public String mAuthor;

    @SerializedName("title")
    public String mTitle;

    @SerializedName("description")
    public String mDescription;

    @SerializedName("url")
    public String mArticleUrl;

    @SerializedName("urlToImage")
    public String mImageUrl;

    @SerializedName("publishedAt")
    public String mPublishDate;

}
