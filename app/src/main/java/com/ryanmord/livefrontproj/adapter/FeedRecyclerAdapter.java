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
 * Adapter to be used on the RecyclerView on the FeedFragment
 */
public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {

    /**
     * Interface for notifying of item click events
     */
    public interface OnFeedItemClickListener {

        /**
         * Called whenever a recycler item is clicked
         *
         * @param item  FeedItem containing data corresponding to the
         *              item that was clicked
         */
        void feedItemClicked(FeedItemViewHolder item);
    }


    /**
     * Application context
     */
    private Context mContext;

    /**
     * Click listener to notify of click events
     */
    private OnFeedItemClickListener mClickListener;

    /**
     * Data currently being represented by the adapter.
     */
    private List<FeedItem> mData = new ArrayList<>();


    /**
     * Instantiate new Adapter
     *
     * @param c Application context
     */
    public FeedRecyclerAdapter(Context c) {
        mContext = c;
    }


    /**
     * Set structure of FeedItems for use in adapter
     *
     * @param data  Items/Data to use in view population
     */
    public void setData(List<FeedItem> data) {
        mData.clear();

        if(data != null) {
            mData.addAll(data);
        }

        notifyDataSetChanged();
    }


    /**
     * Set listener to notify on click events
     *
     * @param listener  Listener to notify
     */
    public void setItemClickListener(OnFeedItemClickListener listener) {
        mClickListener = listener;
    }


    /**
     * {@inheritDoc}
     *
     * @param parent    ViewGroup being inflated into
     * @param viewType  Identifier for type of ViewHolder to create
     *
     * @return  ViewHolder item to use in RecyclerView
     */
    @Override
    public FeedItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed_item, parent, false);

        return new FeedItemViewHolder(v);
    }


    /**
     * {@inheritDoc}
     *
     * @param holder    Holder to populate with data
     * @param position  Position of holder in adapter
     */
    @Override
    public void onBindViewHolder(final FeedItemViewHolder holder, int position) {
        final String IMAGE_TRANS_NAME = "image";

        FeedItem currentItem = mData.get(position);

        holder.setFeedItem(mContext, currentItem);
        ViewCompat.setTransitionName(holder.getImage(), IMAGE_TRANS_NAME + holder.getAdapterPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener != null) {
                    mClickListener.feedItemClicked(holder);
                }
            }
        });
    }


    /**
     * {@inheritDoc}
     * @return  Number of items in the adapter
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


}
