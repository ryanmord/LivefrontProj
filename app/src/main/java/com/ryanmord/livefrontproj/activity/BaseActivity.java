package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.adapter.viewholder.FeedItemViewHolder;
import com.ryanmord.livefrontproj.api.DataRetriever;
import com.ryanmord.livefrontproj.api.DataRetriever.OnFeedDataRetrieved;
import com.ryanmord.livefrontproj.fragment.DetailsFragment;
import com.ryanmord.livefrontproj.fragment.FeedFragment;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Base activity that manages fragments and data transfer.
 */
public class BaseActivity extends AppCompatActivity implements FeedFragment.IFeedFragmentCallback, OnFeedDataRetrieved {

    /**
     * Toolbar reference. Added as support actionbar
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * Bar layout used to expand/collapse toolbar on RecyclerView
     * scroll in FeedFragment.
     */
    @BindView(R.id.bar_layout)
    AppBarLayout mBarLayout;

    /**
     * Reference to instantiated fragments for interaction
     * and reuse.
     */
    private FeedFragment mFeedFragment;
    private DetailsFragment mDetailsFragment;

    /**
     * Data retriever for API interaction and data retrieval.
     */
    private DataRetriever mDataRetriever;


    /**
     *  {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Timber.d("BaseActivity launched and content view set");

        ButterKnife.bind(this); //Bind views to class
        setSupportActionBar(mToolbar);
        mDataRetriever = new DataRetriever(this);

        Timber.d("Views bound, toolbar set. Instantiating and launching FeedFragment");

        if(savedInstanceState != null) {
            mFeedFragment = (FeedFragment) getFragmentManager().getFragment(savedInstanceState, FeedFragment.TAG);
            if(mFeedFragment != null) {
                mFeedFragment.setCallback(this); //must be re-set since old activity callback was destroyed
            }

            mDetailsFragment = (DetailsFragment) getFragmentManager().getFragment(savedInstanceState, DetailsFragment.TAG);
        } else {
            //Inflate and commit feed fragment
            mFeedFragment = FeedFragment.newInstance();
            mFeedFragment.setCallback(this);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.base_fragment_holder, mFeedFragment)
                    .commit();
        }

    }


    /**
     * {@inheritDoc}
     * @param outState  Bundle for storing data through state changes
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mFeedFragment != null) {
            getFragmentManager().putFragment(outState, FeedFragment.TAG, mFeedFragment);
        }

        if(mDetailsFragment != null && mDetailsFragment.isAdded()) {
            getFragmentManager().putFragment(outState, DetailsFragment.TAG, mDetailsFragment);
        }
    }


    /**
     *  {@inheritDoc}
     */
    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        //Create new DetailsFragment and pass transition name of the image being shared
        Timber.d("Clicked feed item received. Instantiating DetailsFragment...");
        mDetailsFragment = DetailsFragment.newInstance(item.getFeedItem(), ViewCompat.getTransitionName(item.getImage()));


        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        //Check for required Android version for shared element transition.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Timber.d("Acceptable SDK version for shared element transition. Configuring shared elements...");
            //Configure transitions for entering/exiting transaction
            Transition moveTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            Transition fadeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);

            mFeedFragment.setSharedElementReturnTransition(moveTransform);
            mFeedFragment.setExitTransition(fadeTransform);

            mDetailsFragment.setSharedElementEnterTransition(moveTransform);
            mDetailsFragment.setEnterTransition(fadeTransform);

            mDetailsFragment.setSharedElementEnterTransition(moveTransform);
            mDetailsFragment.setEnterTransition(new android.transition.Fade(android.transition.Fade.IN));

            //Set element to be shared between fragments
            transaction.addSharedElement(item.getImage(), ViewCompat.getTransitionName(item.getImage()));
        }

        Timber.d("Performing DetailsFragment transaction...");
        //Commit transaction and expand bar layout
        transaction.replace(R.id.base_fragment_holder, mDetailsFragment)
                .addToBackStack(null)
                .commit();


        mBarLayout.setExpanded(true, true);

    }


    /**
     *  {@inheritDoc}
     */
    @Override
    public void refreshFeed() {
        Timber.d("Performing feed refresh on request.");
        if(mDataRetriever != null) {
            mDataRetriever.fetchFeed(this);
        }
    }


    /**
     *  {@inheritDoc}
     */
    @Override
    public void onReceive(Exception exception, List<FeedItem> data) {
        Timber.d("Feed refresh response received");

        //If data retrieval was unsuccessful
        if(exception != null || data == null) {
            Timber.e("FEED REFRESH ERROR :: " + exception);
            mFeedFragment.showErrorSnackbar();
        } else {
            Timber.d("Feed refresh successful. Passing to fragment.");
            //Otherwise set feed data
            mFeedFragment.setFeedData(data);
        }
    }


}
