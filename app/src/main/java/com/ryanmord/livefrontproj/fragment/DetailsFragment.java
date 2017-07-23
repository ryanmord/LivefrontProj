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
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


    public static DetailsFragment newInstance(FeedItem item, String imageTransitionName) {
        DetailsFragment frag = new DetailsFragment();
        frag.mItem = item;

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

        String subString = String.format("%s by %s", mItem.getDateString(getActivity()), mItem.mAuthor);

        mTitle.setText(mItem.mTitle);
        mSubtext.setText(subString);
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

        Glide.with(getActivity())
                .load(mItem.mImageUrl)
                .centerCrop()
                .into(mHeaderImage);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
