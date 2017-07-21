package com.ryanmord.livefrontproj.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.widget.FrameLayout;

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

    FeedFragment mFeedFragment;

    private DataRetriever mDataRetriever = new DataRetriever();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        mFeedFragment = FeedFragment.newInstance(BaseActivity.this);
        mFeedFragment.setExitTransition(new Fade(Fade.OUT));
        mFeedFragment.setEnterTransition(new Fade(Fade.IN));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.base_fragment_holder, mFeedFragment)
                .addToBackStack(null)
                .commit();

        fetchData();
    }


    private void fetchData() {
        mDataRetriever.fetchFeed(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case android.R.id.home:
                getSupportFragmentManager().popBackStackImmediate();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        DetailsFragment d = DetailsFragment.newInstance(item.getFeedItem(), ViewCompat.getTransitionName(item.mImage));
        d.setEnterTransition(new Fade(Fade.IN));
        d.setExitTransition(new Fade(Fade.OUT));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.base_fragment_holder, d)
                .addSharedElement(item.mImage, ViewCompat.getTransitionName(item.mImage))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void feedRefreshed() {
        fetchData();
    }

    @Override
    public void onReceive(FeedData data) {
        mFeedFragment.setFeedData(data.articles);
    }
}
