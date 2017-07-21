package com.ryanmord.livefrontproj.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ryanmord.livefrontproj.R;
import com.ryanmord.livefrontproj.fragment.FeedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.base_fragment_holder)
    FrameLayout mFragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.base_fragment_holder, new FeedFragment())
        .commit();
    }
}
