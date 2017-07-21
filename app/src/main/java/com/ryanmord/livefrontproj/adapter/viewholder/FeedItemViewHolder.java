package com.ryanmord.livefrontproj.adapter.viewholder;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ryanmord.livefrontproj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanmord on 7/21/17.
 */

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_title)
    TextView mTitle;

    @BindView(R.id.item_author)
    TextView mAuthor;

    @BindView(R.id.item_date)
    TextView mDate;


    public FeedItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setAuthor(String author) {
        mAuthor.setText(author);
    }

    public void setDate(String date) {
        mDate.setText(date);
    }


}
