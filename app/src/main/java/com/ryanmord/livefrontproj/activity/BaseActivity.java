package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.adapter.viewholder.FeedItemViewHolder;
import com.ryanmord.livefrontproj.api.DataRetriever;
import com.ryanmord.livefrontproj.api.DataRetriever.OnFeedDataRetrieved;
import com.ryanmord.livefrontproj.fragment.DetailsFragment;
import com.ryanmord.livefrontproj.fragment.FeedFragment;
import com.ryanmord.livefrontproj.objects.FeedData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements FeedFragment.IFeedFragmentCallback, OnFeedDataRetrieved {

    @BindView(R.id.base_fragment_holder)
    FrameLayout mFragmentHolder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    FeedFragment mFeedFragment;

    private DataRetriever mDataRetriever;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);



        mDataRetriever = new DataRetriever(this);

        mFeedFragment = FeedFragment.newInstance(BaseActivity.this);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.base_fragment_holder, mFeedFragment)
                .commit();


        fetchData();
    }


    private boolean fetchData() {
        return mDataRetriever.fetchFeed(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        DetailsFragment d = DetailsFragment.newInstance(item.getFeedItem(), ViewCompat.getTransitionName(item.mImage));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                android.R.animator.fade_in, android.R.animator.fade_out);

        transaction.add(R.id.base_fragment_holder, d)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean feedRefreshed() {
        return fetchData();
    }

    @Override
    public void onReceive(FeedData data) {
        mFeedFragment.setFeedData(data.articles);
    }


}
