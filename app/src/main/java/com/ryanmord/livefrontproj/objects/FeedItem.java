package com.ryanmord.livefrontproj.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ryanmord.livefrontproj.util.DateUtils;

import org.joda.time.DateTime;

/**
 * Class used to hold data pertaining to a single feed item.
 */
public class FeedItem implements Parcelable {

    /**
     * Data keys used to parse data values from JSON response.
     */
    private static final String KEY_AUTHOR         = "author";
    private static final String KEY_TITLE          = "title";
    private static final String KEY_DESCRIPTION    = "description";
    private static final String KEY_ARTICLE_URL    = "url";
    private static final String KEY_IMAGE_URL      = "urlToImage";
    private static final String KEY_PUBLISH_DATE   = "publishedAt";



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



    public static final String TAG = "feed";



    private FeedItem(String author, String title, String desc, String articleUrl, String imageUrl, DateTime publishDate) {
        mAuthor = author;
        mTitle = title;
        mDescription = desc;
        mArticleUrl = articleUrl;
        mImageUrl = imageUrl;
        mPublishDate = publishDate;
    }



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






    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mArticleUrl);
        dest.writeString(mImageUrl);
        dest.writeSerializable(mPublishDate);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will raises an exception
     * Parcelable protocol requires a Parcelable.Creator object
     * called CREATOR
     */
    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {

        public FeedItem createFromParcel(Parcel in) {

            String      author = in.readString();
            String      title = in.readString();
            String      description = in.readString();
            String      articleUrl = in.readString();
            String      imageUrl = in.readString();
            DateTime    publishDate = (DateTime) in.readSerializable();

            return new FeedItem(author, title, description, articleUrl, imageUrl, publishDate);
        }

        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
