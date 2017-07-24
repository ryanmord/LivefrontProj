package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
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

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.bar_layout)
    AppBarLayout mBarLayout;

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

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.base_fragment_holder, mFeedFragment)
                .addToBackStack(null)
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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Transition moveTransform = TransitionInflater.from(this).inflateTransition(R.transition.move_transition);
            Transition fadeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);

            fadeTransform.excludeTarget(R.id.bar_layout, true);

            mFeedFragment.setSharedElementReturnTransition(moveTransform);
            mFeedFragment.setExitTransition(fadeTransform);

            d.setSharedElementEnterTransition(moveTransform);
            d.setEnterTransition(fadeTransform);

            d.setSharedElementEnterTransition(moveTransform);
            d.setEnterTransition(new android.transition.Fade(android.transition.Fade.IN));

            transaction.addSharedElement(item.mImage, ViewCompat.getTransitionName(item.mImage));
        }

        transaction.replace(R.id.base_fragment_holder, d)
                .addToBackStack(null)
                .commit();

        mBarLayout.setExpanded(true, true);
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
