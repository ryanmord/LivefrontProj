package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.api.DataRetriever;
import com.ryanmord.livefrontproj.fragment.FeedFragment;
import com.ryanmord.livefrontproj.objects.FeedData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.base_fragment_holder)
    FrameLayout mFragmentHolder;

    FeedFragment mFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        DataRetriever d = new DataRetriever();
        d.fetchFeed(new DataRetriever.OnFeedDataRetrieved() {
            @Override
            public void onReceive(FeedData data) {
                mFeedFragment = FeedFragment.newInstance(data.articles);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.base_fragment_holder, mFeedFragment)
                        .commit();
            }
        });

    }
}
