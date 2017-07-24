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
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.objects.FeedItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanmord on 7/20/17.
 */

public class DetailsFragment extends Fragment {

    @BindView(R.id.details_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.details_fab)
    FloatingActionButton mActionButton;

    @BindView(R.id.details_title)
    TextView    mTitle;

    @BindView(R.id.details_subtext)
    TextView    mSubtext;

    @BindView(R.id.details_summary)
    TextView    mSummary;

    private FeedItem mItem;
    private String mHeaderTransitionName = "";


    public static DetailsFragment newInstance(FeedItem item, String imageTransitionName) {
        DetailsFragment frag = new DetailsFragment();
        frag.mItem = item;
        frag.mHeaderTransitionName = imageTransitionName;

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
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);

        StringBuilder s = new StringBuilder();
        if(mItem.mPublishDate != null) {
            s.append(String.format("%s", mItem.getDateString(getActivity())));
        }

        if(mItem.mAuthor != null) {
            s.append(String.format(TextUtils.isEmpty(s) ? "By " : " by %s", mItem.mAuthor));
        }

        mTitle.setText(mItem.mTitle);
        mSubtext.setText(s.toString());
        mSummary.setText(mItem.mDescription);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mItem.mArticleUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ViewCompat.setTransitionName(mHeaderImage, mHeaderTransitionName);
        Glide.with(getActivity())
                .load(mItem.mImageUrl)
                .centerCrop()
                .into(mHeaderImage);

        animateFabIn();

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        ActionBar t = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(t != null) {
            t.setDisplayHomeAsUpEnabled(true);
            t.setTitle("Details");
        }
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    public void animateFabIn() {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        a.setDuration(1000);
        mActionButton.startAnimation(a);
    }

    private void animateFabOut(Animation.AnimationListener listener) {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        a.setAnimationListener(listener);
        a.setDuration(1000);
        mActionButton.startAnimation(a);
    }
}
