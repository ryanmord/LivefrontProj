package com.ryanmord.livefrontproj.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import timber.log.Timber;

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

        /**
         * Called to request a data refresh.
         */
        void refreshFeed();
    }

    /**
     * Tag for storing and maintaining fragment through state changes
     */
    public static final String TAG = "feed";

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
    private List<FeedItem> mData = new ArrayList<>();

    /**
     * Tag used to store and retrieve feed data during state
     * changes
     */
    private final String DATA_TAG = "feed_data_tag";

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
     * @return  Newly instantiated FeedFragment instance for use.
     */
    public static FeedFragment newInstance() {
        FeedFragment frag = new FeedFragment();
        frag.setRetainInstance(true);
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

        if(savedInstanceState != null) {
            List<FeedItem> savedItems = savedInstanceState.getParcelableArrayList(DATA_TAG);
            if(savedItems != null) {
                mData.clear();
                mData.addAll(savedItems);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param outState  Bundle for maintaining state during state changes
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DATA_TAG, (ArrayList<? extends Parcelable>) mData);
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
        Timber.d("View inflated and bound. Continuing setup...");

        RecyclerView.LayoutManager m;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            m = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        } else {
            // Portrait
            m = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

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
        Timber.d("Configuring toolbar");

        ActionBar t = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(t != null) {
            t.setDisplayHomeAsUpEnabled(false);
            t.setDisplayShowTitleEnabled(false);
        } else {
            Timber.e("TOOLBAR NULL");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();
        if(mData.size() == 0 && mCallback != null) {
            Timber.d("FeedFragment started. Refreshing feed");
            mSwipeRefresh.setRefreshing(true);
            mCallback.refreshFeed();
        }
    }


    /**
     * Set fragment callback to notify of events
     *
     * @param callback  Callback implementation
     */
    public void setCallback(IFeedFragmentCallback callback) {
        mCallback = callback;
    }


    /**
     * Sets the data to be shown in the feed. This will
     * clear any existing data from the list.
     *
     * @param items Structure of data to be shown
     */
    public void setFeedData(List<FeedItem> items) {
        Timber.d("Fragment handling new data.");
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
        mSwipeRefresh.setRefreshing(false);
        if(mFragmentView != null) {
            Timber.d("Displaying error snackbar");
            Snackbar.make(mFragmentView, R.string.something_went_wrong_check_connectivity, 3000).show();
        } else {
            Timber.e("Fragment view null... Showing error with Toast");
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
            Timber.d("Item click received in fragment. Passing to callback");
            mCallback.feedItemClicked(item);
        } else {
            Timber.d("Item click received in fragment but callback was null");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onRefresh() {
        if(mCallback != null) {
            Timber.d("Feed refresh received. Passing to callback");
            mCallback.refreshFeed();
        } else {
            Timber.d("Feed refresh received but callback was null");
            mSwipeRefresh.setRefreshing(false);
        }
    }



}
