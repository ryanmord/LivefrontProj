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
import android.view.ViewTreeObserver;
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
 * Created by ryanmord on 7/20/17.
 */

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FeedRecyclerAdapter.OnFeedItemClickListener {

    public interface IFeedFragmentCallback {
        void feedItemClicked(FeedItemViewHolder item);
        boolean refreshFeed();
    }


    @BindView(R.id.feed_main_recycler)
    RecyclerView mFeedRecycler;

    @BindView(R.id.feed_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    List<FeedItem> mData;
    FeedRecyclerAdapter mAdapter;
    IFeedFragmentCallback mCallback;

    View mFragmentView;


    public static FeedFragment newInstance(IFeedFragmentCallback callback) {
        FeedFragment frag = new FeedFragment();
        frag.mCallback = callback;

        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        ActionBar t = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(t != null) {
            t.setDisplayHomeAsUpEnabled(false);
            t.setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if(mCallback != null) {
            mCallback.refreshFeed();
        }
    }


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





    public void showErrorSnackbar() {
        if(mFragmentView != null) {
            Snackbar.make(mFragmentView, "Something went wrong! Check connectivity!", 3000).show();
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        if(mCallback != null) {
            mCallback.feedItemClicked(item);
        }
    }





    @Override
    public void onRefresh() {
        if(mCallback != null) {
            boolean requestSuccess = mCallback.refreshFeed();
            if(!requestSuccess) {
                mSwipeRefresh.setRefreshing(false);
                showErrorSnackbar();
            }
        }
    }



}
