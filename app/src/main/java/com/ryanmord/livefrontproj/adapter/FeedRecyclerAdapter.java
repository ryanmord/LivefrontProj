package com.ryanmord.livefrontproj.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
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

    public interface OnFeedItemClickListener {
        void feedItemClicked(FeedItemViewHolder item);
    }


    private Context mContext;
    private OnFeedItemClickListener mClickListener;
    private List<FeedItem> mData = new ArrayList<>();


    public FeedRecyclerAdapter(Context c) {
        mContext = c;
    }

    public void setData(List<FeedItem> data) {
        mData.clear();

        if(data != null) {
            mData.addAll(data);
        }
    }

    public void setItemClickListener(OnFeedItemClickListener listener) {
        mClickListener = listener;
    }


    @Override
    public FeedItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed_item, parent, false);

        return new FeedItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FeedItemViewHolder holder, int position) {
        FeedItem currentItem = mData.get(position);

        holder.setFeedItem(mContext, currentItem);
        ViewCompat.setTransitionName(holder.mImage, "image"+holder.getAdapterPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener != null) {
                    mClickListener.feedItemClicked(holder);
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return mData.size();
    }


}
