package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.support.design.widget.AppBarLayout;
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
import com.ryanmord.livefrontproj.objects.FeedData;
import com.ryanmord.livefrontproj.objects.FeedItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Base activity that manages fragments and data retrieval.
 */
public class BaseActivity extends AppCompatActivity implements FeedFragment.IFeedFragmentCallback, OnFeedDataRetrieved {

    /**
     * Toolbar reference. Added as support actionbar
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * Bar layout used to expand/collapse toolbar on recyclerview
     * scroll in FeedFragment.
     */
    @BindView(R.id.bar_layout)
    AppBarLayout mBarLayout;

    /**
     * Reference to instantiated feed fragment for interaction
     * and reuse.
     */
    private FeedFragment mFeedFragment;

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

        ButterKnife.bind(this); //Bind views to class
        setSupportActionBar(mToolbar);

        mDataRetriever = new DataRetriever(this);

        //Inflate and commit feed fragment
        mFeedFragment = FeedFragment.newInstance(BaseActivity.this);
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.base_fragment_holder, mFeedFragment)
                .commit();
    }




    /**
     *  {@inheritDoc}
     */
    @Override
    public void feedItemClicked(FeedItemViewHolder item) {
        //Create new DetailsFragment and pass transition name of the image being shared
        DetailsFragment d = DetailsFragment.newInstance(item.getFeedItem(), ViewCompat.getTransitionName(item.getImage()));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        //Check for required Android version for shared element transition.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //Configure transitions for entering/exiting transaction
            Transition moveTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            Transition fadeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);

            mFeedFragment.setSharedElementReturnTransition(moveTransform);
            mFeedFragment.setExitTransition(fadeTransform);

            d.setSharedElementEnterTransition(moveTransform);
            d.setEnterTransition(fadeTransform);

            d.setSharedElementEnterTransition(moveTransform);
            d.setEnterTransition(new android.transition.Fade(android.transition.Fade.IN));

            //Set element to be shared between fragments
            transaction.addSharedElement(item.getImage(), ViewCompat.getTransitionName(item.getImage()));
        }

        //Commit transaction and expand bar layout
        transaction.replace(R.id.base_fragment_holder, d)
                .addToBackStack(null)
                .commit();

        mBarLayout.setExpanded(true, true);
    }





    /**
     *  {@inheritDoc}
     */
    @Override
    public void refreshFeed() {
        mDataRetriever.fetchFeed(this);
    }







    /**
     *  {@inheritDoc}
     */
    @Override
    public void onReceive(boolean error, List<FeedItem> data) {

        //If data retrieval was unsuccessful
        if(error || data == null) {
            mFeedFragment.showErrorSnackbar();
        } else {
            //Otherwise set feed data
            mFeedFragment.setFeedData(data);
        }

    }


}
