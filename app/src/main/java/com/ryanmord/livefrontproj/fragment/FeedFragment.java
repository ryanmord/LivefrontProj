package com.ryanmord.livefrontproj.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.adapter.FeedRecyclerAdapter;
import com.ryanmord.livefrontproj.api.DataRetriever;
import com.ryanmord.livefrontproj.objects.FeedData;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanmord on 7/20/17.
 */

public class FeedFragment extends Fragment {

    @BindView(R.id.feed_main_recycler)
    RecyclerView mFeedRecycler;

    List<FeedItem> mData = new ArrayList<>();

    public static FeedFragment newInstance(List<FeedItem> items) {
        FeedFragment f = new FeedFragment();
        f.mData.addAll(items);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, v);

        FeedRecyclerAdapter a = new FeedRecyclerAdapter(getActivity(), mData);
        RecyclerView.LayoutManager m = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mFeedRecycler.setLayoutManager(m);
        mFeedRecycler.setAdapter(a);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();




    }
}
