package com.ryanmord.livefrontproj.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.objects.FeedItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Fragment for displaying more detailed information for a specific article.
 */
public class DetailsFragment extends Fragment {

    /**
     * ImageView for article image. (Shared Element)
     */
    @BindView(R.id.details_header_image)
    ImageView mHeaderImage;

    /**
     * Floating action button for full article navigation
     */
    @BindView(R.id.details_fab)
    FloatingActionButton mActionButton;

    /**
     * TextView for displaying article title
     */
    @BindView(R.id.details_title)
    TextView    mTitle;

    /**
     * TextView for time/author subtext
     */
    @BindView(R.id.details_subtext)
    TextView    mSubtext;

    /**
     * TextView for article description
     */
    @BindView(R.id.details_summary)
    TextView    mSummary;

    /**
     * FeedItem being represented by this fragment
     */
    private FeedItem mItem;

    /**
     * String value holding the name of the shared element
     * coming from the FeedFragment. This must be set
     * dynamically since each item in the FeedRecycler has
     * a different transition name.
     */
    private String mHeaderTransitionName;

    /**
     * Boolean indicating if exit animations are currently
     * in progress. Used to prevent multiple back arrow clicks while
     * animation is happening.
     */
    private boolean mIsExiting = false;


    /**
     * Create new details fragment for the given FeedItem.
     *
     * @param item  FeedItem object containing the data to be displayed
     * @param imageTransitionName   Transition name of the incoming image being shared
     *                              from FeedFragment.
     *
     * @return  Newly instantiated DetailsFragment.
     */
    public static DetailsFragment newInstance(FeedItem item, String imageTransitionName) {
        DetailsFragment frag = new DetailsFragment();
        frag.mItem = item;
        frag.mHeaderTransitionName = imageTransitionName;

        return frag;
    }




    /**
     * {@inheritDoc}
     * @param savedInstanceState Bundle containing saved instance data
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    /**
     * {@inheritDoc}
     *
     * @param inflater  LayoutInflater to inflate fragment view
     * @param container Parent container view to hold fragment
     * @param savedInstanceState    Bundle holding saved instance data
     *
     * @return  Inflated fragment view.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);
        Timber.d("View inflated and bound. Continuing setup...");

        //Build details string.
        StringBuilder s = new StringBuilder();
        if(mItem.getPublishDate() != null) {
            s.append(mItem.getDateString(getActivity()));
        }

        if(mItem.getAuthor() != null) {
            s.append(String.format(TextUtils.isEmpty(s) ? getString(R.string.by_space) :
                    getString(R.string.space_by_var), mItem.getAuthor()));
        }

        //Populate TextViews
        mTitle.setText(mItem.getTitle());
        mSubtext.setText(s.toString());
        mSummary.setText(mItem.getDescription());

        //Set click listener to launch browser and navigate to full
        //article via provided URL
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = mItem.getArticleUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(i);

            }
        });


        //FAB animation initiated in transition listener will not be fired
        //on devices lower than Lollipop, so animate immediately.
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            animateFabIn();
        }


        //Set shared transition name of header image.
        ViewCompat.setTransitionName(mHeaderImage, mHeaderTransitionName);
        Picasso.with(getActivity())
                .load(mItem.getImageUrl())
                .into(mHeaderImage);

        return v;
    }


    /**
     * {@inheritDoc}
     *
     * @param item  MenuItem that was clicked
     *
     * @return  True if this class handled to selection,
     *          false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //If exiting is being performed, dont initialize animations
        if(item.getItemId() == android.R.id.home) {
            Timber.d("Menu back arrow clicked.");
            if (!mIsExiting) {
                Timber.d("Animating FAB off screen...");
                mIsExiting = true;
                animateFabOut(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mIsExiting = false;
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }

    }


    /**
     * {@inheritDoc}
     *
     * @param menu  Menu to populate
     * @param inflater  Inflater to inflate menu resources
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Timber.d("Configuring toolbar");

        ActionBar t = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(t != null) {
            t.setDisplayHomeAsUpEnabled(true);
            t.setDisplayShowTitleEnabled(false);
        } else {
            Timber.e("TOOLBAR NULL");
        }
    }


    /**
     * {@inheritDoc}
     *
     * Overridden to apply listener for further animations once
     * image has been set.
     *
     * @param transition    Enter transition being set on this fragment
     */
    @Override
    public void setSharedElementEnterTransition(Transition transition) {
        super.setSharedElementEnterTransition(transition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Timber.d("Shared element enter transition received. Adding listener.");
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    animateFabIn();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener(this);
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
        }
    }


    /**
     * Perform animation to show the floating action button
     */
    public void animateFabIn() {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.roll_in_right);
        mActionButton.startAnimation(a);
        mActionButton.setVisibility(View.VISIBLE);
    }

    /**
     * Perform animation to remove the floating action button from view
     *
     * @param listener  Listener to set on animation
     */
    private void animateFabOut(Animation.AnimationListener listener) {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.roll_out_right);
        a.setAnimationListener(listener);
        mActionButton.startAnimation(a);
    }
}
