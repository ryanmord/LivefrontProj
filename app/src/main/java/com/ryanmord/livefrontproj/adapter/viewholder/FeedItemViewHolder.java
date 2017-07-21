package com.ryanmord.livefrontproj.adapter.viewholder;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.objects.FeedItem;
import com.ryanmord.livefrontproj.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanmord on 7/21/17.
 */

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private FeedItem mItem;

    @BindView(R.id.item_image)
    public CircleImageView mImage;

    @BindView(R.id.item_title)
    public TextView mTitle;

    @BindView(R.id.item_author)
    public TextView mAuthor;

    @BindView(R.id.item_date)
    public TextView mDate;


    public FeedItemViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;

        ButterKnife.bind(this, itemView);
    }

    public void setFeedItem(Context c, FeedItem item) {
        mItem = item;

        mTitle.setText(item.mTitle);
        mAuthor.setText(item.mAuthor);
        mDate.setText("");

        Glide.with(c)
                .load(item.mImageUrl)
                .into(mImage);
    }

    public FeedItem getFeedItem() {
        return mItem;
    }

}