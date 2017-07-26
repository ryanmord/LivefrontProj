package com.ryanmord.livefrontproj.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.objects.FeedItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Viewholder class used by FeedRecyclerAdapter for individual feed items
 */

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    /**
     * FeedItem object containing item data
     * to be displayed
     */
    private FeedItem mItem;


    /**
     * ImageView for displaying article header-image
     */
    @BindView(R.id.item_image)
    ImageView mImage;

    /**
     * TextView holding the title text
     */
    @BindView(R.id.item_title)
    TextView mTitle;

    /**
     * TextView holding time since published
     */
    @BindView(R.id.item_date)
    TextView mDate;


    /**
     * Instantiate new FeedItemViewHolder with given view
     *
     * @param itemView  Inflated view to be bound
     */
    public FeedItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


    /**
     * Set and format ViewHolder with data contained in
     * given FeedItem
     *
     * @param c     Context for image loading.
     * @param item  FeedItem object containing data to be
     *              shown.
     */
    public void setFeedItem(Context c, FeedItem item) {
        mItem = item;

        //Set title text
        mTitle.setText(item.getTitle());

        //If item has publish date, set string and visibility.
        //Otherwise hide date text
        if(item.getPublishDate() != null) {
            mDate.setVisibility(View.VISIBLE);
            mDate.setText(item.getDateString(c));
        } else {
            mDate.setVisibility(View.GONE);
        }

        //Load image into view
        String imgUrl = item.getImageUrl();
        if(imgUrl != null && !imgUrl.isEmpty()) {
            Picasso.with(c)
                    .load(imgUrl)
                    .into(mImage);
        }
    }


    /**
     * @return  Feed item being presented
     */
    public FeedItem getFeedItem() {
        return mItem;
    }


    /**
     * @return Article ImageView.
     */
    public ImageView getImage() {
        return mImage;
    }
}
