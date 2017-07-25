package com.ryanmord.livefrontproj.objects;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.ryanmord.livefrontproj.util.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Class used to hold data pertaining to a single feed item.
 */
public class FeedItem {

    /**
     * Data keys used to parse data values from JSON response.
     */
    private final String KEY_AUTHOR         = "author";
    private final String KEY_TITLE          = "title";
    private final String KEY_DESCRIPTION    = "description";
    private final String KEY_ARTICLE_URL    = "url";
    private final String KEY_IMAGE_URL      = "urlToImage";
    private final String KEY_PUBLISH_DATE   = "publishedAt";



    /**
     * Article author
     */
    @SerializedName(KEY_AUTHOR)
    private String mAuthor;

    /**
     * Title of article
     */
    @SerializedName(KEY_TITLE)
    private String mTitle;

    /**
     * Article description
     */
    @SerializedName(KEY_DESCRIPTION)
    private String mDescription;

    /**
     * URL to full article
     */
    @SerializedName(KEY_ARTICLE_URL)
    private String mArticleUrl;

    /**
     * URL to article image
     */
    @SerializedName(KEY_IMAGE_URL)
    private String mImageUrl;

    /**
     * Article publish date
     */
    @SerializedName(KEY_PUBLISH_DATE)
    private DateTime mPublishDate;


    /**
     * Formats the article publish date to a string representing
     * the time since publish.
     *
     * @param context   Application context
     * @return  String formatted with time since publish.
     */
    public String getDateString(Context context) {
        return DateUtils.durationSinceDate(context, mPublishDate);
    }


    /**
     * @return Article author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * @return  Article title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * @return  Article description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @return  Article Url
     */
    public String getArticleUrl() {
        return mArticleUrl;
    }

    /**
     * @return  Article image url
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * @return  Article publish date
     */
    public DateTime getPublishDate() {
        return mPublishDate;
    }
}
