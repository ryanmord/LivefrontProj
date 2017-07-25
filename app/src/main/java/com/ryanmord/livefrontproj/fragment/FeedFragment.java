package com.ryanmord.livefrontproj.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.adapter.FeedRecyclerAdapter;
import com.ryanmord.livefrontproj.adapter.viewholder.FeedItemViewHolder;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment used to maintain the main feed of data
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FeedRecyclerAdapter.OnFeedItemClickListener {

    /**
     * Interface used to callback to BaseActivity and
     * handle events
     */
    public interface IFeedFragmentCallback {
        /**
         * Method called when an item in the feed recycler
         * has been clicked.
         *
         * @param item  Item ViewHolder that was clicked.
         */
        void feedItemClicked(FeedItemViewHolder item);
        void refreshFeed();
    }


    /**
     * RecyclerView used to display article data
     */
    @BindView(R.id.feed_main_recycler)
    RecyclerView mFeedRecycler;

    /**
     * SwipeRefresh layout used to refresh data on
     * user request
     */
    @BindView(R.id.feed_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    /**
     * Structure containing data currently being
     * represented in the RecyclerView
     */
    private List<FeedItem> mData;

    /**
     * Adapter attached to recycler view
     */
    private FeedRecyclerAdapter mAdapter;

    /**
     * Callback instance to notify of events
     */
    private IFeedFragmentCallback mCallback;

    /**
     * Reference to this fragments view.
     */
    private View mFragmentView;


    /**
     * Create new FeedFragment instance with given callback
     *
     * @param callback  Callback implementation to notify of events.
     * @return  Newly instantiated FeedFragment instance for use.
     */
    public static FeedFragment newInstance(IFeedFragmentCallback callback) {
        FeedFragment frag = new FeedFragment();
        frag.mCallback = callback;

        return frag;
    }


    /**
     * {@inheritDoc}
     * @param savedInstanceState Bundle containing saved state data
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    /**
     * {@inheritDoc}
     *
     * @param inflater  Inflater to be used in fragment view inflation
     * @param container Parent container view to inflate fragment view into
     * @param savedInstanceState    Bundle containing saved state data
     *
     * @return  Inflated fragment view to show
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, mFragmentView);

        RecyclerView.LayoutManager m = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new FeedRecyclerAdapter(getActivity());
        mAdapter.setData(mData);
        mAdapter.setItemClickListener(this);

        mFeedRecycler.setHasFixedSize(true);
        mFeedRecycler.setLayoutManager(m);
        mFeedRecycler.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(this);

        return mFragmentView;
    }


    /**
     * {@inheritDoc}
     *
     * @param menu      Menu to add items to
     * @param inflater  Inflater for inflating menu resources
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        ActionBar t = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(t != null) {
            t.setDisplayHomeAsUpEnabled(false);
            t.setDisplayShowTitleEnabled(false);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();
        if(mCallback != null) {
            mSwipeRefresh.setRefreshing(true);
            mCallback.refreshFeed();
        }
    }


    /**
     * Sets the data to be shown in the feed. This will
     * clear any existing data from the list.
     *
     * @param items Structure of data to be shown
     */
    public void setFeedData(List<FeedItem> items) {
        if(mData == null) {
            mData = new ArrayList<>();
        }

        mData.clear();
        mData.addAll(items);

        if(mAdapter != null) {
            mAdapter.setData(mData);
        }

        mSwipeRefresh.setRefreshing(false);
    }


    /**
     * Displays the appropriate error message to notify the
     * user that something went wrong. Will use a Snackbar
     * primarily, but if an error happens before fragment view
     * is available, a Toast will be used.
     */
    public void showErrorSnackbar() {
        if(mFragmentView != null) {
            Snackbar.make(mFragmentView, R.string.something_went_wrong_check_connectivity, 3000).show();
        } else {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param item  FeedItem containing data corresponding to the
     */
    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        if(mCallback != null) {
            mCallback.feedItemClicked(item);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onRefresh() {
        if(mCallback != null) {
            mCallback.refreshFeed();
        }
    }



}
