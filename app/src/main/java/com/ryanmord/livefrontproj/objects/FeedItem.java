package com.ryanmord.livefrontproj.objects;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.ryanmord.livefrontproj.util.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by ryanmord on 7/20/17.
 */

public class FeedItem {

    private final DateTimeFormatter DISPLAY_FORMATTER_DATETIME = DateTimeFormat.forPattern("MMM dd h:mm a").withZone(DateTimeZone.getDefault());


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
    public DateTime mPublishDate;



    public String getDateString(Context context) {
        return DateUtils.durationSinceDate(context, mPublishDate);
//        return DISPLAY_FORMATTER_DATETIME.print(mPublishDate);
    }
}
