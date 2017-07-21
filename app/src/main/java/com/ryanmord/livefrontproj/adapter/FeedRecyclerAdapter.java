package com.ryanmord.livefrontproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.adapter.viewholder.FeedItemViewHolder;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanmord on 7/21/17.
 */

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {

    private Context mContext;
    private List<FeedItem> mData = new ArrayList<>();

    public FeedRecyclerAdapter(Context c, List<FeedItem> data) {
        mContext = c;
        if(data != null) {
            mData.addAll(data);
        }
    }




    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed_item, parent, false);
        return new FeedItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedItemViewHolder holder, int position) {
        FeedItem currentItem = mData.get(position);

        holder.setTitle(currentItem.mTitle);
        holder.setAuthor(currentItem.mAuthor);
        holder.setDate(currentItem.mPublishDate);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
